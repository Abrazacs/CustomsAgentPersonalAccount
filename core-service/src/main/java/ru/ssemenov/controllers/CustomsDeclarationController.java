package ru.ssemenov.controllers;

import org.springframework.http.ResponseEntity;
import ru.ssemenov.dtos.CustomsDeclarationDto;
import ru.ssemenov.dtos.PageDto;

import java.util.UUID;

public interface CustomsDeclarationController {

    PageDto<CustomsDeclarationDto> getAllCustomsDeclarationByVatCode(String vatCode, Integer page, Integer pageSize, String sortBy, String numberPart);

    CustomsDeclarationDto getCustomsDeclarationById(UUID id);

    ResponseEntity<String> addNewCustomsDeclaration(CustomsDeclarationDto customsDeclarationDto);

    ResponseEntity<String> deleteCustomsDeclarationById(UUID id);
}
