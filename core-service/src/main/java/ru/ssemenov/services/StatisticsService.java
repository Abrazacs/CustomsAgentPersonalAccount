package ru.ssemenov.services;

import org.springframework.stereotype.Service;
import ru.ssemenov.dtos.StatisticsDto;
import ru.ssemenov.entities.CustomsDeclaration;
import ru.ssemenov.exceptions.ResourceNotFoundException;
import ru.ssemenov.utils.CustomsDeclarationStatusEnum;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public interface StatisticsService {

    public StatisticsDto getStatistics();
}
