package ru.ssemenov.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ssemenov.controllers.StatisticsController;
import ru.ssemenov.dtos.StatisticsDto;
import ru.ssemenov.services.StatisticsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/declarations/statistics")
public class StatisticsControllerImpl implements StatisticsController {

    private final StatisticsService statisticsService;

    @Override
    public StatisticsDto getStatistics() {
        return statisticsService.getStatistics();
    }
}
