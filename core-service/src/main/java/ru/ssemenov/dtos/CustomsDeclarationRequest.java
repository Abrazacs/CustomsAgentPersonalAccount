package ru.ssemenov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * A Request for the {@link ru.ssemenov.entities.CustomsDeclaration} entity
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class CustomsDeclarationRequest implements Serializable {

    @NotBlank(message = "Поле наименование грузоотправителя обязательно для заполнения")
    @Schema(description = "Наименование грузоотправителя", required = true, example = "Trade inc")
    private final String consignor;

    @NotBlank(message = "Поле ИНН грузополучателя обязательно для заполнения")
    @Schema(description = "ИНН грузополучателя", required = true, example = "7777777777")
    private final String vatCode;

    @NotBlank(message = "Поле данные по инвойсу обязательно для заполнения")
    @Schema(description = "Данные по инвойсу", required = true, example = "ab-234")
    private final String invoiceData;

    @Min(1)
    @NotNull(message = "Поле фактурная стоимость груза обязательно для заполнения")
    @Schema(description = "Фактурная стоимость груза", required = true, example = "120000")
    private final BigDecimal goodsValue;

    @NotNull(message = "Поле статус не может быть пустым")
    @Schema(description = "Статус декларации", required = true, example = "REGISTERED")
    private final String status;

    @NotNull(message = "Индентификационый номер ДТ обязателен")
    @Schema(description = "Идентификационный номер ДТ", required = true, example = "ca26b177-dfdb-40c5-beaf-1e1672111e52")
    private final UUID id;

    @Schema(description = "Номер декларации", example = "10228010/031022/6543623")
    private final String number;

    @NotNull
    @Schema(description = "Дата подачи ДТ", required = true, example = "2022-11-05T13:22:49+10:00")
    private final OffsetDateTime dateOfSubmission;

    @Schema(description = "Дата выпуска или отказа в выпуске ДТ", example = "2022-11-05T13:22:49+10:00")
    private final OffsetDateTime dateOfRelease;


}
