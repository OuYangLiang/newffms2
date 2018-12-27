insert into user_profile(user_oid, user_name, user_alias, gender, phone, email, login_id, login_pwd, user_type_oid, create_time, create_by)
values(4, '访客', '访客', 'Male', '', '', 'guest', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 1, now(), '初始化数据');

insert into role_profile(role_oid, role_desc, user_type_oid) values (2, '访客', 1);

insert into user_role(user_oid, role_oid) values (4, 2);

insert into role_operation(role_oid, opn_oid) values(2, 1);
insert into role_operation(role_oid, opn_oid) values(2, 2);
insert into role_operation(role_oid, opn_oid) values(2, 4);
insert into role_operation(role_oid, opn_oid) values(2, 9);
insert into role_operation(role_oid, opn_oid) values(2, 24);
insert into role_operation(role_oid, opn_oid) values(2, 26);

