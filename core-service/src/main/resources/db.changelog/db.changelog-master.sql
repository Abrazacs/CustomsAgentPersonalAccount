-- liquibase formatted sql

-- changeset ilyha:1669183295526-1
CREATE TABLE customs_declaration
(
    number             VARCHAR(255) NOT NULL,
    status             VARCHAR(255) NOT NULL,
    consignee          VARCHAR(255) NOT NULL,
    vat_code           VARCHAR(255) NOT NULL,
    invoice_data       VARCHAR(255) NOT NULL,
    goods_value        DECIMAL      NOT NULL,
    date_of_submission TIMESTAMP with time zone,
    date_of_release    TIMESTAMP with time zone,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_customs_declaration PRIMARY KEY (number)
);