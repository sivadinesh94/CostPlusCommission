INSERT INTO `costcommission`.`user`
(`created_by`,`created_time_stamp`,`updated_time_stamp`,`deleted`,`active`,`email_id`,`full_name`,`location`,`login_id`,`designation_id`,`password`)
VALUES('1',now(),now(),0,1,'suma@1cloudhub.com','Admin','Mumbai','admin','1','admin');

insert into  costcommission.role(name,created_time_stamp,updated_time_stamp) values('Admin',now(),now());
insert into  costcommission.role(name,created_time_stamp,updated_time_stamp) values('Preparer',now(),now());
insert into  costcommission.role(name,created_time_stamp,updated_time_stamp) values('Reviewer',now(),now());
insert into  costcommission.role(name,created_time_stamp,updated_time_stamp) values('Country Manager',now(),now());
insert into  costcommission.role(name,created_time_stamp,updated_time_stamp) values('GBS Management',now(),now());

insert into costcommission.designation(name,created_time_stamp,updated_time_stamp) values('Executive',now(),now());
insert into costcommission.designation(name,created_time_stamp,updated_time_stamp) values('Supervisor',now(),now());
insert into costcommission.designation(name,created_time_stamp,updated_time_stamp) values('Manager',now(),now());

INSERT INTO `costcommission`.`category`(`created_time_stamp`,`updated_time_stamp`,`category_seq`,`name`)
VALUES(now(),now(),1,'Cost+ scope');
INSERT INTO `costcommission`.`category`(`created_time_stamp`,`updated_time_stamp`,`category_seq`,`name`)
VALUES(now(),now(),2,'Out Of Cost+ scope');

INSERT INTO `costcommission`.`sub_category`
(`created_time_stamp`,`updated_time_stamp`,`name`,`sub_category_seq`,`category_id`)
VALUES(now(),now(),'Front Office',1,1);
INSERT INTO `costcommission`.`sub_category`
(`created_time_stamp`,`updated_time_stamp`,`name`,`sub_category_seq`,`category_id`)
VALUES(now(),now(),'Documentation',2,1);
