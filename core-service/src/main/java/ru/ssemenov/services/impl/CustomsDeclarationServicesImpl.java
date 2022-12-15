package ru.ssemenov.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ssemenov.dtos.CustomsDeclarationRequest;
import ru.ssemenov.dtos.StatisticsResponse;
import ru.ssemenov.entities.CustomsDeclaration;
import ru.ssemenov.exceptions.ResourceException;
import ru.ssemenov.exceptions.ResourceNotFoundException;
import ru.ssemenov.repositories.CustomsDeclarationRepository;
import ru.ssemenov.repositories.specifications.CustomsDeclarationSpecifications;
import ru.ssemenov.services.CustomsDeclarationServices;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomsDeclarationServicesImpl implements CustomsDeclarationServices {

    private final String BASE_STATUS = "FILED";
    private final CustomsDeclarationRepository customsDeclarationRepository;

    @Override
    public CustomsDeclaration findById(UUID id) {
        return customsDeclarationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Декларация с id:" + id + " не найдена"));
    }

    @Override
    public Page<CustomsDeclaration> findAll(String vatCode, Integer pageNo, Integer pageSize, String sortBy, String numberPart) {
        Specification<CustomsDeclaration> spec = Specification.where(CustomsDeclarationSpecifications.equalsVatCode(vatCode));
        if (numberPart != null) {
            spec = spec.and(CustomsDeclarationSpecifications.numberLike(numberPart));
        }
        return customsDeclarationRepository.findAll(spec, PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
    }

    @Override
    public void addCustomsDeclaration(CustomsDeclarationRequest customsDeclarationRequest) {
        UUID trace = UUID.randomUUID();
        log.info("Start save declaration declarationRequest={}, traceId={}", customsDeclarationRequest, trace);
        CustomsDeclaration customsDeclaration = CustomsDeclaration.builder()
                .status(BASE_STATUS)
                .consignor(customsDeclarationRequest.getConsignor())
                .vatCode(customsDeclarationRequest.getVatCode())
                .invoiceData(customsDeclarationRequest.getInvoiceData())
                .goodsValue(customsDeclarationRequest.getGoodsValue())
                .build();
        try {
            customsDeclarationRepository.save(customsDeclaration);
            log.info("Declaration successfully saved, traceId={}", trace);
        } catch (DataIntegrityViolationException e) {
            log.error("Error save new declaration, error={}, traceId={}", e.getMessage(), trace);
            throw new ResourceException("Декларация " + customsDeclarationRequest + " не сохранена!");
        }
    }

    @Override
    public void deleteById(UUID id) {
        UUID trace = UUID.randomUUID();
        log.info("Start delete declaration id={}, traceId={}", id, trace);
        try {
            customsDeclarationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error delete declaration with id={}, error={}, traceId={}", id, e.getMessage(), trace);
            throw new ResourceException("Декларация с id=" + id + " не существует!");
        }
        log.info("Declaration successfully deleted, traceId={}", trace);
    }

    @Override
    public StatisticsResponse getStatistics() {
        List<CustomsDeclaration> customsDeclarationsByLastMonth = customsDeclarationRepository.getDeclarationOfSubmissionByLastMonth();
        List<CustomsDeclaration> totalDeclarations = customsDeclarationRepository.findAll();
        return StatisticsResponse.builder()
                .averageDeclarationTimeOfReleaseByLastMonth(averageDeclarationTimeOfReleaseByLastMonth(customsDeclarationsByLastMonth))
                .percentDeclarationFirstHalfOfTheDay(percentDeclarationFirstHalfOfTheDay(customsDeclarationsByLastMonth.size()))
                .percentDeclarationIssuedWithOneDayOfMonth(percentDeclarationIssuedWithOneDayOfMonth(customsDeclarationsByLastMonth, totalDeclarations))
                .quantityDeclarationInwWork(quantityDeclarationInwWork(totalDeclarations))
                .build();
    }

    private String averageDeclarationTimeOfReleaseByLastMonth(List<CustomsDeclaration> customsDeclarationsByLastMonth) {
        OptionalDouble averageTimeSeconds = customsDeclarationsByLastMonth.stream().map(c -> c.getDateOfRelease().toEpochSecond() - c.getDateOfSubmission().toEpochSecond())
                .mapToLong(t -> t).average();
        return LocalTime.ofSecondOfDay((long) averageTimeSeconds.getAsDouble()).toString();
    }
    
    private Integer percentDeclarationFirstHalfOfTheDay(int countCustomsDeclarationsByLastMonth) {
        long countCustomDeclaration = customsDeclarationRepository.getDeclarationOfSubmissionByLastMonth()
                .stream().filter(c -> c.getDateOfSubmission().getHour() < 12).collect(Collectors.toList()).stream().count();
        return (int) countCustomDeclaration/countCustomsDeclarationsByLastMonth;
    }

    private Integer percentDeclarationIssuedWithOneDayOfMonth(List<CustomsDeclaration> customsDeclarationsByLastMonth, List<CustomsDeclaration> totalDeclarations) {
        return customsDeclarationsByLastMonth.size()/totalDeclarations.size();
    }

    private Integer quantityDeclarationInwWork(List<CustomsDeclaration> customsDeclarationsAll) {
        long countCustomsDeclarationNoRelease = customsDeclarationsAll.stream()
                .filter(c -> !c.getStatus().equals("RELEASE"))
                .filter(c -> !c.getStatus().equals("RELEASE_DENIED"))
                .collect(Collectors.toList()).stream().count();
        return (int) countCustomsDeclarationNoRelease/customsDeclarationsAll.size();
    }

}
