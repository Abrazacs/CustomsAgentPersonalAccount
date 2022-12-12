package ru.ssemenov.controllers;

import org.springframework.stereotype.Controller;
import ru.ssemenov.dtos.StatisticsDto;

@Controller
public interface StatisticsController {

    StatisticsDto getStatistics();
}
