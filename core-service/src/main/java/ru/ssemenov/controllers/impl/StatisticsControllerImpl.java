package ru.ssemenov.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final StatisticsService statisticsService;

    @Override
    @GetMapping
    public StatisticsDto getStatistics() {
        return new StatisticsDto(statisticsService.getAverageDurationOfRelease(),
                statisticsService.getShareOfSubmittedDeclarationsBeforeNoonByMonth(),
                statisticsService.getShareOfReleasedDeclarationsWithinOneDayByMonth(),
                statisticsService.getDeclarationsCountInProgress());
    }
}
