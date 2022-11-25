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
	
-- changeset ilyha:1669165796353-3
insert into customs_declaration
    (id, number, status, consignee, vat_code, invoice_data, goods_value, date_of_submission, date_of_release)
values
    ('684e4ea4-cac9-4b33-843e-d26274ff9f7e', '10226010/220211/0003344', 'RELEASE', 'Trade inc', '7777777777', 'ab-234', 120000.00, '2022-10-19T10:23:54+10:00', '2022-10-19T10:29:32+10:00'),
    ('f6f1c3fd-c6b0-43c1-a2f5-ea8e0e0fd2db', '10228010/031022/3340262', 'RELEASE', 'Trade inc', '7777777777', 'ab-235', 50000.00, '2022-10-19T10:23:54+10:00', '2022-10-19T10:29:32+10:00'),
    ('b9a6a74e-5caf-4988-a425-d35ba93f3323', '10228010/031022/3340234', 'RELEASE', 'Trade inc', '7777777777', 'ab-236', 45721.63, '2022-10-19T10:23:54+10:00', '2022-10-19T10:29:32+10:00'),
    ('4a74dbfd-25c8-4098-8446-c36046476fb9', '10228010/031022/3340272', 'RELEASE', 'Trade inc', '7777777777', 'ab-237', 12515.07, '2022-10-19T10:23:54+10:00', '2022-10-19T10:29:32+10:00'),
    ('127b06f3-aa62-4f93-bb29-f7de160480a8', '10228010/031022/3340273', 'RELEASE_DENIED', 'Trade inc', '7777777777', 'ab-238', 2000.00, '2022-10-19T10:23:54+10:00', '2022-10-19T10:29:32+10:00'),

    ('698d530c-e151-49bd-9035-81ce60af16c5', '10228010/220211/2367314', 'RELEASE', 'ColaDrinks', '6767676767', 'ab-239', 26421.92, '2022-10-25T15:48:21+10:00', '2022-10-25T15:55:03+10:00'),
    ('764d3d91-66a4-416e-84fd-08056ebf2174', '10228010/031022/2367325', 'RELEASE', 'ColaDrinks', '6767676767', 'ab-240', 15156.27, '2022-10-25T15:48:21+10:00', '2022-10-25T15:55:03+10:00'),
    ('986dd13a-8b79-4410-934d-2775198f7969', '10228010/031022/2367359', 'RELEASE', 'ColaDrinks', '6767676767', 'ab-241', 39541.38, '2022-10-25T15:48:21+10:00', '2022-10-25T15:55:03+10:00'),

    ('ca26b177-dfdb-40c5-beaf-1e1672111e52', '10228010/031022/6543623', 'RELEASE', 'Ferum CO ltd', '1234567891', 'ab-242', 35915.61, '2022-11-05T13:22:49+10:00', '2022-11-05T13:46:12+10:00'),
    ('f67d269e-8c6e-4427-801e-abff8dec334f', '10228010/031022/6543656', 'RELEASE_DENIED', 'Ferum CO ltd', '1234567891', 'ab-243', 84120.50, '2022-11-05T13:22:49+10:00', '2022-11-05T13:46:12+10:00');