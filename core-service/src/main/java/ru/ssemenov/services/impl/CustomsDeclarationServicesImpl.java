package ru.ssemenov.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ssemenov.dtos.CustomsDeclarationDto;
import ru.ssemenov.entities.CustomsDeclaration;
import ru.ssemenov.exceptions.ResourceException;
import ru.ssemenov.exceptions.ResourceNotFoundException;
import ru.ssemenov.repositories.CustomsDeclarationRepository;
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
    public Page<CustomsDeclaration> findAll(String vatCode, Integer pageNo, Integer pageSize, String sortBy) {
        return customsDeclarationRepository.findAllByVatCode(vatCode, PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
    }

    @Override
    public void addCustomsDeclaration(CustomsDeclarationDto customsDeclarationDto) {
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
        } catch (DataIntegrityViolationException e) {
            log.warn(e.getRootCause() + " " + e.getMessage());
            throw new ResourceException("Декларация с number:" + customsDeclarationDto.getNumber() + " уже существует!");
        }
    }

    @Override
    public void deleteById(UUID id) {
        findById(id);
        customsDeclarationRepository.deleteById(id);
    }
}
