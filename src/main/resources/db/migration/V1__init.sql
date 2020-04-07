/*
* Engine: MYSQL
* VERSION: 0.0.1
* Description: database init
*/

create table users
(
    id   varchar(100) not null unique primary key,
    name varchar(100)
) engine = InnoDB;
