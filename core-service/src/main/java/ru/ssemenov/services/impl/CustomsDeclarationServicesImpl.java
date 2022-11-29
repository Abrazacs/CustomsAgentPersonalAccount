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
import ru.ssemenov.dtos.CustomsDeclarationDto;
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
    public void addCustomsDeclaration(CustomsDeclarationDto customsDeclarationDto) {
        UUID trace = UUID.randomUUID();
        log.info(String.format("Start save declaration declarationDto=%s, traceId=%s", customsDeclarationDto, trace));
        CustomsDeclaration customsDeclaration = CustomsDeclaration.builder()
                .number(customsDeclarationDto.getNumber())
                .status(customsDeclarationDto.getStatus())
                .consignee(customsDeclarationDto.getConsignee())
                .vatCode(customsDeclarationDto.getVatCode())
                .invoiceData(customsDeclarationDto.getInvoiceData())
                .goodsValue(customsDeclarationDto.getGoodsValue())
                .dateOfSubmission(customsDeclarationDto.getDateOfSubmission())
                .dateOfRelease(customsDeclarationDto.getDateOfRelease())
                .build();
        try {
            customsDeclarationRepository.save(customsDeclaration);
            log.info(String.format("Declaration successfully saved, traceId=%s", trace));
        } catch (DataIntegrityViolationException e) {
            log.error(String.format("Error save new declaration, error=%s, traceId=%s", e.getMessage(), trace));
            throw new ResourceException("Декларация с number:" + customsDeclarationDto.getNumber() + " уже существует!");
        }
    }

    @Override
    public void deleteById(UUID id) {
        UUID trace = UUID.randomUUID();
        log.info(String.format("Start delete declaration id=%s, traceId=%s", id, trace));
        try {
            customsDeclarationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(String.format("Error delete declaration with id=%s, error=%s, traceId=%s", id, e.getMessage(), trace));
            throw new ResourceException("Декларация с id:" + id + " не существует!");
        }
        log.info(String.format("Declaration successfully deleted, traceId=%s", trace));
    }
}
