package ru.ssemenov.entities;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Entity of the customs declaration
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customs_declaration")
public class CustomsDeclaration {
    @Id
    @NotNull
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @ColumnDefault("random_uuid()")
    @Column(name = "id")
    private UUID id;

    @Column(name = "number", unique = true)
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
