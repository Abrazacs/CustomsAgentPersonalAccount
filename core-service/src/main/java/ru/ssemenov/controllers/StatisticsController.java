package ru.ssemenov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ssemenov.dtos.StatisticsDto;

@Controller
public interface StatisticsController {

    @Override
    @GetMapping("/statistics")
    public StatisticsDto getStatistics() {
        return statisticsService.getStatistics();
    }
}
