package ru.ssemenov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * A DTO for the {@link ru.ssemenov.entities.CustomsDeclaration} entity
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomsDeclarationDto implements Serializable {
    @Schema(description = "ID декларации", required = true, example = "684e4ea4-cac9-4b33-843e-d26274ff9f7e")
    private final UUID id;

    @NotNull
    @Schema(description = "Номер декларации", required = true, example = "10226010/220211/0003344")
    private final String number;

    @NotNull
    @Schema(description = "Статус таможенной декларации", required = true, example = "RELEASE")
    private final String status;

    @NotNull
    @Schema(description = "Наименование грузоотправителя", required = true, example = "Trade inc")
    private final String consignee;

    @NotNull
    @Schema(description = "ИНН грузополучателя", required = true, example = "7777777777")
    private final String vatCode;

    @NotNull
    @Schema(description = "Данные по инвойсу", required = true, example = "ab-234")
    private final String invoiceData;

    @NotNull
    @Schema(description = "Фактурная стоимость груза", required = true, example = "120000")
    private final BigDecimal goodsValue;

    @Schema(description = "Дата и время подачи ДТ", required = true, example = "2022-10-19 10:23:54")
    private final OffsetDateTime dateOfSubmission;

    @Schema(description = "Дата и время выпуска / отказа", required = true, example = "2022-10-19 10:23:54")
    private final OffsetDateTime dateOfRelease;
}
