package ru.ssemenov.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * Entity of the customs declaration
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "customs_declaration")
public class CustomsDeclaration {
    @Id
    @Column(name = "number")
    private String number;

    @NotNull
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "consignee")
    private String consignee;

    @NotNull
    @Column(name = "vat_Code")
    private String vatCode;

    @NotNull
    @Column(name = "invoice_data")
    private String invoiceData;

    @NotNull
    @Column(name = "goods_value")
    private BigDecimal goodsValue;

    @Column(name = "date_of_submission")
    private OffsetDateTime dateOfSubmission;

    @Column(name = "date_of_release")
    private OffsetDateTime dateOfRelease;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
