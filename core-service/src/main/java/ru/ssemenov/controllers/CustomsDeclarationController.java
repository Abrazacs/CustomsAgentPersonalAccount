package ru.ssemenov.controllers;

import org.springframework.http.ResponseEntity;
import ru.ssemenov.dtos.CustomsDeclarationRequest;
import ru.ssemenov.dtos.CustomsDeclarationResponse;
import ru.ssemenov.dtos.PageDto;

import java.util.UUID;

public interface CustomsDeclarationController {

    PageDto<CustomsDeclarationResponse> getAllCustomsDeclarationByVatCode(String vatCode, Integer page, Integer pageSize, String sortBy, String numberPart);

    CustomsDeclarationResponse getCustomsDeclarationById(UUID id);

    ResponseEntity<String> addNewCustomsDeclaration(CustomsDeclarationRequest customsDeclarationRequest);

    ResponseEntity<String> deleteCustomsDeclarationById(UUID id);
}
