package ru.ssemenov.services;

import org.springframework.data.domain.Page;
import ru.ssemenov.dtos.CustomsDeclarationDto;
import ru.ssemenov.entities.CustomsDeclaration;

public interface CustomsDeclarationServices {

    CustomsDeclaration findByNumber(String number);

    Page<CustomsDeclaration> findAll(Integer pageNo, Integer pageSize, String sortBy);

    void addCustomsDeclaration(CustomsDeclarationDto productDto);

    void deleteByNumber(String number);
}
