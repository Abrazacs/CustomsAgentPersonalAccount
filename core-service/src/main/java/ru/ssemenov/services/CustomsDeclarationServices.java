package ru.ssemenov.services;

import org.springframework.data.domain.Page;
import ru.ssemenov.dtos.CustomsDeclarationRequest;
import ru.ssemenov.dtos.StatisticsResponse;
import ru.ssemenov.entities.CustomsDeclaration;

import java.util.UUID;

public interface CustomsDeclarationServices {

    CustomsDeclaration findById(UUID id);

    Page<CustomsDeclaration> findAll(String vatCode, Integer pageNo, Integer pageSize, String sortBy, String numberPart);

    void addCustomsDeclaration(CustomsDeclarationRequest customsDeclarationRequest);

    void deleteById(UUID id);

    StatisticsResponse getStatistics();
}
