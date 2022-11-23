create table roles (
    id int primary key,
    name varchar(255) unique not null
);

insert into roles (id, name) values
(1, 'user'),
(2, 'admin');
