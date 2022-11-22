package ru.ssemenov.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "customs_declaration")
public class CustomsDeclaration {
    @Id
    @Column(name = "number")
    private String number;

    @Column(name = "status")
    private String status;

    @Column(name = "consignee")
    private String consignee;

    @Column(name = "vat_Code")
    private String vatCode;

    @Column(name = "invoice_data")
    private String invoiceData;

    @Column(name = "goods_value")
    private BigDecimal goodsValue;

    @Column(name = "date_of_submission")
    private OffsetDateTime dateOfSubmission;

    @Column(name = "date_of_release")
    private String dateOfRelease;
}
