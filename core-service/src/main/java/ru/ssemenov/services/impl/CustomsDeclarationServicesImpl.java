package ru.ssemenov.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.ssemenov.dtos.CustomsDeclarationDto;
import ru.ssemenov.entities.CustomsDeclaration;
import ru.ssemenov.services.CustomsDeclarationServices;

@Service
public class CustomsDeclarationServicesImpl implements CustomsDeclarationServices {
    @Override
    public CustomsDeclaration findByNumber(String number) {
        //TODO add method implementation
        return null;
    }

    @Override
    public Page<CustomsDeclaration> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        //TODO add method implementation
        return null;
    }

    @Override
    public void addCustomsDeclaration(CustomsDeclarationDto productDto) {
        //TODO add method implementation
    }

    @Override
    public void deleteByNumber(String number) {
        //TODO add method implementation
    }
}
