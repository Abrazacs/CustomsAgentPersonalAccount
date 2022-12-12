package ru.ssemenov.services;

import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public interface StatisticsService {

    LocalTime getAverageDurationOfRelease();

    Double getShareOfSubmittedDeclarationsBeforeNoonByMonth();

    Double getShareOfReleasedDeclarationsWithinOneDayByMonth();

    Long getDeclarationsCountInProgress();
}
