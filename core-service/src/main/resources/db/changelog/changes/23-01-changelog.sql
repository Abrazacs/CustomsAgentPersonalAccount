-- liquibase formatted sql

-- changeset ilyha:1669204902461-1
CREATE TABLE customs_declaration
(
    id                 UUID         NOT NULL,
    number             VARCHAR(255) NOT NULL,
    status             VARCHAR(255) NOT NULL,
    consignee          VARCHAR(255) NOT NULL,
    vat_code           VARCHAR(255) NOT NULL,
    invoice_data       VARCHAR(255) NOT NULL,
    goods_value        DECIMAL      NOT NULL,
    date_of_submission TIMESTAMP with time zone,
    date_of_release    TIMESTAMP with time zone,
    created_at         TIMESTAMP default current_timestamp,
    updated_at         TIMESTAMP default current_timestamp,
    CONSTRAINT pk_customs_declaration PRIMARY KEY (id)
);

-- changeset ilyha:1669211639290-2
ALTER TABLE customs_declaration
    ADD CONSTRAINT uc_customs_declaration_number UNIQUE (number);

