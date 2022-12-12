package ru.ssemenov.services;

import org.springframework.data.domain.Page;
import ru.ssemenov.dtos.CustomsDeclarationRequest;
import ru.ssemenov.entities.CustomsDeclaration;

import java.io.File;
import java.util.UUID;

public interface CustomsDeclarationServices {

    CustomsDeclaration findById(UUID id);

    Page<CustomsDeclaration> findAll(String vatCode, Integer pageNo, Integer pageSize, String sortBy, String numberPart);

    UUID addCustomsDeclaration(CustomsDeclarationRequest customsDeclarationRequest);

    void deleteById(UUID id);

    File export(String vatCode);
}
