package ru.ssemenov.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * A DTO for the {@link ru.ssemenov.entities.CustomsDeclaration} entity
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomsDeclarationDto implements Serializable {
    private final String number;
    @NotNull
    private final String status;
    @NotNull
    private final String consignee;
    @NotNull
    private final String vatCode;
    @NotNull
    private final String invoiceData;
    @NotNull
    private final BigDecimal goodsValue;
    private final OffsetDateTime dateOfSubmission;
    private final OffsetDateTime dateOfRelease;
}
