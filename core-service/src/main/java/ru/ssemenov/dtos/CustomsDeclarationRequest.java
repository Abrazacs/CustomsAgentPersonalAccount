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

    @NotBlank
    @Schema(description = "Наименование грузоотправителя", required = true, example = "Trade inc")
    private final String consignor;

    @NotBlank
    @Schema(description = "ИНН грузополучателя", required = true, example = "7777777777")
    private final String vatCode;

    @NotBlank
    @Schema(description = "Данные по инвойсу", required = true, example = "ab-234")
    private final String invoiceData;

    @Min(1)
    @NotNull
    @Schema(description = "Фактурная стоимость груза", required = true, example = "120000")
    private final BigDecimal goodsValue;
}
