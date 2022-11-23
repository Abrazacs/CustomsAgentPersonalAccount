-- liquibase formatted sql

--changeset author: sergey

create table roles (
    id          UUID            primary key,
    name        varchar(255)    unique not null
);

insert into roles (id, name)
values('f3db87e9-738e-46c8-990f-30206f38131a', 'user'),
       ('0b103c7e-9110-43cf-8236-4db3f12095c6', 'admin');

create table users(
    login       varchar(255)    primary key,
    password    varchar(255)    not null,
    email       varchar(255)    not null,
    companyVAT  varchar(255)    not null
);

insert into users (login, password, email, companyVAT)
values ('admin', '$2a$12$i2wJXQxdTdUMK7Zg5TSrX.LLuqI0SMKeiNmP3xdF3UEeaSipB6Nqa', 'admin@admin.com', '7777777777'),
       ('abrazacs', '$2a$12$NfP7TBABXE1TzpoTfEjlAOoFGmPAJe6HVyGc3DSjuuaUWcb6WOyC2','sergeysemenov87@gmail.com','6767676767'),
       ('nonpostlife', '$2a$12$NfP7TBABXE1TzpoTfEjlAOoFGmPAJe6HVyGc3DSjuuaUWcb6WOyC2', 'mail@mail.com', '1234567891');

create table users_roles (
    user_id     varchar(255)    not null references users (login),
    role_id     varchar(255)    not null references roles (id),
    primary key (user_id, role_id)
);

insert into users_roles (user_id, role_id)
values ('admin', 'f3db87e9-738e-46c8-990f-30206f38131a'),
       ('admin', '0b103c7e-9110-43cf-8236-4db3f12095c6'),
       ('abrazacs', 'f3db87e9-738e-46c8-990f-30206f38131a'),
       ('nonpostlife', 'f3db87e9-738e-46c8-990f-30206f38131a');
