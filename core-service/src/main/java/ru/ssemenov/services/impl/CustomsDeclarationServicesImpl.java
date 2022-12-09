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
import ru.ssemenov.entities.CustomsDeclaration;
import ru.ssemenov.exceptions.ResourceException;
import ru.ssemenov.exceptions.ResourceNotFoundException;
import ru.ssemenov.repositories.CustomsDeclarationRepository;
import ru.ssemenov.repositories.specifications.CustomsDeclarationSpecifications;
import ru.ssemenov.services.CustomsDeclarationServices;

import java.util.UUID;

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
            log.info("Declaration with id={} successfully deleted, traceId={}", id, trace);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error delete declaration with id={}, error={}, traceId={}", id, e.getMessage(), trace);
            throw new ResourceException("Декларация с id=" + id + " не существует!");
        }
    }
}
