package ru.ssemenov.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomsDeclarationDto {
    private String number;
    private String status;
    private String consignee;
    private String vatCode;
    private String invoiceData;
    private BigDecimal goodsValue;
    private OffsetDateTime dateOfSubmission;
    private String dateOfRelease;
}
