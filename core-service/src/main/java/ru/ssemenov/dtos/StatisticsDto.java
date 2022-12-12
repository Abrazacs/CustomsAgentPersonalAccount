package ru.ssemenov.dtos;

import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@RequiredArgsConstructor
public class StatisticsDto {

    private final LocalTime averageDurationOfRelease;
    private final Double shareOfSubmittedDeclarationsBeforeNoonByMonth;
    private final Double shareOfReleasedDeclarationsWithinOneDayByMonth;
    private final Long declarationsCountInProgress;
}
