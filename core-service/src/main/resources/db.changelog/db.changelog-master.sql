create table customs_declaration
(
    number              varchar(255) primary key,
    status              varchar(255),
    consignee           varchar(255),
    vat_code            varchar(50),
    invoice_data        varchar(255),
    good_value          numeric(20, 2),
    date_of_submission  timestamp,
    date_of_release     timestamp
);

insert into customs_declaration (number, status, consignee, vat_code, invoice_data, good_value)
values ('10226010/220211/0003344', 'REGISTERED', 'ООО СПЕЦСТРОЙ', '7732712383', '' , 120000.00),
       ('10262721/220211/0001289', 'CREATED', 'ООО ВЕСНА', '7818204756', '' ,50000.00);