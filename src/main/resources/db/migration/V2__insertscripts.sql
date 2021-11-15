
insert into customer (customerId, customerName) VALUES ('12345678', 'customer1');
insert into customer (customerId, customerName) VALUES ('12345679', 'customer2');
insert into customer (customerId, customerName) VALUES ('12345670', 'customer3');

insert into phone (phoneId, phoneNumber, phoneType, isActive, customerId) VALUES ('8123456', '9123456789', 'work', false, '12345678');
insert into phone (phoneId, phoneNumber, phoneType, isActive, customerId) VALUES ('8123457', '9123456788', 'work', true,'12345678');
insert into phone (phoneId, phoneNumber, phoneType, isActive, customerId) VALUES ('8123458', '9123456787', 'work', false, '12345678');

insert into phone (phoneId, phoneNumber, phoneType, isActive, customerId) VALUES ('8123466', '9123456689', 'work', false, '12345679');
insert into phone (phoneId, phoneNumber, phoneType, isActive, customerId) VALUES ('8123467', '9123456488', 'work', false, '12345679');
insert into phone (phoneId, phoneNumber, phoneType, isActive, customerId) VALUES ('8123468', '9123456587', 'work', false, '12345679');

insert into phone (phoneId, phoneNumber, phoneType, isActive, customerId) VALUES ('8123476', '9123486789', 'work', false, '12345670');
insert into phone (phoneId, phoneNumber, phoneType, isActive, customerId) VALUES ('8123477', '9123496788', 'work', false, '12345670');
insert into phone (phoneId, phoneNumber, phoneType, isActive, customerId) VALUES ('8123478', '9123406787', 'work', false, '12345670');
