package ru.ssemenov.services;

import org.springframework.data.domain.Page;
import ru.ssemenov.dtos.CustomsDeclarationDto;
import ru.ssemenov.entities.CustomsDeclaration;

import java.util.UUID;

public interface CustomsDeclarationServices {

    CustomsDeclaration findById(UUID id);

    Page<CustomsDeclaration> findAll(String vatCode, Integer pageNo, Integer pageSize, String sortBy);

    void addCustomsDeclaration(CustomsDeclarationDto customsDeclarationDto);

    void deleteById(UUID id);
}
