package ru.ssemenov.services;

import org.springframework.stereotype.Service;
import ru.ssemenov.dtos.StatisticsDto;

@Service
public interface StatisticsService {

    StatisticsDto getStatistics();
}
