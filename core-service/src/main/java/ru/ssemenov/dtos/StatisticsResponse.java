package ru.ssemenov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsResponse {
    @Schema(description = "Средний срок выпуска ДТ (за месяц)", required = true)
    private String averageDeclarationTimeOfReleaseByLastMonth;

    @Schema(description = "% ДТ поданных до 12:00 (за месяц)", required = true)
    private Integer percentDeclarationFirstHalfOfTheDay;

    @Schema(description = "% ДТ выпущенных в течение одного дня (за месяц)", required = true)
    private Integer percentDeclarationIssuedWithOneDayOfMonth;

    @Schema(description = "Кол-во ДТ в работе", required = true)
    private Integer quantityDeclarationInWork;

}
