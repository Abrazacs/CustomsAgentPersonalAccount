package ru.ssemenov.services;

import org.springframework.stereotype.Service;
import ru.ssemenov.dtos.StatisticsDto;

import java.util.Optional;

@Service
public interface StatisticsService {

    StatisticsDto getStatistics();
}
