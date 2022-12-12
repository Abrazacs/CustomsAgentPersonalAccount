package ru.ssemenov.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class StatisticsDto {

    private final LocalTime averageDurationOfRelease;
    private final Double shareOfSubmittedDeclarationsBeforeNoonByMonth;
    private final Double shareOfReleasedDeclarationsWithinOneDayByMonth;
    private final Long declarationsCountInProgress;
}
