DROP TABLE IF EXISTS phone;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
                          customerId varchar(50) not null primary key,
                          customerName varchar(50)
);

CREATE TABLE phone (
                       phoneId varchar(50) not null primary key ,
                       phoneNumber varchar(10) not null unique,
                       phoneType varchar(50) not null,
                       isActive boolean default false,
                       customerId varchar(50) references customer(customerId)
);

create index phone_idx_phonenumber
    on phone (phoneNumber);