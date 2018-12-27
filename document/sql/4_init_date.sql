insert into user_type(user_type_oid, user_type_desc) values (1, '普通用户');

insert into user_profile(user_oid, user_name, user_alias, gender, phone, email, icon, remarks, login_id, login_pwd, user_type_oid, create_time, create_by)
values(1, '欧阳亮', '老公', 'Male', '18652022500', 'ouyanggod@gmail.com', 'oyl.jpg', '苏宁：高级架构师', 'oyl', '', 1, now(), '初始化数据');
insert into user_profile(user_oid, user_name, user_alias, gender, phone, email, icon, remarks, login_id, login_pwd, user_type_oid, create_time, create_by)
values(2, '喻敏', '老婆', 'Female', '18652930160', 'yumingirl@gmail.com', 'ym.jpg', '中兴：测试工程师', 'yumin', '', 1, now(), '初始化数据');
insert into user_profile(user_oid, user_name, user_alias, gender, phone, email, icon, remarks, login_id, login_pwd, user_type_oid, create_time, create_by)
values(3, '欧阳晓筱', '宝贝女儿', 'Female', '', '', 'oyxx.jpg', '小可爱', 'oyxx', '', 1, now(), '初始化数据');
update user_profile set login_pwd = '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2';

insert into role_profile(role_oid, role_desc, user_type_oid) values (1, '普通用户', 1);

insert into user_role(user_oid, role_oid) values (1, 1);
insert into user_role(user_oid, role_oid) values (2, 1);
insert into user_role(user_oid, role_oid) values (3, 1);

insert into module(module_oid, module_desc, module_level, show_order, module_link, parent_oid)
values(1, '首页', 1, 1, '/welcome', null);
insert into module(module_oid, module_desc, module_level, show_order, module_link, parent_oid)
values(2, '消费', 1, 2, '/consumption/summary', null);
insert into module(module_oid, module_desc, module_level, show_order, module_link, parent_oid)
values(3, '消费查询', 1, 3, '/report/consumption', null);
insert into module(module_oid, module_desc, module_level, show_order, module_link, parent_oid)
values(4, '收入', 1, 4, '/incoming/summary', null);
insert into module(module_oid, module_desc, module_level, show_order, module_link, parent_oid)
values(5, '收入查询', 1, 5, '/report/incoming', null);
insert into module(module_oid, module_desc, module_level, show_order, module_link, parent_oid)
values(6, '账户', 1, 6, '/account/summary', null);
insert into module(module_oid, module_desc, module_level, show_order, module_link, parent_oid)
values(7, '类别', 1, 7, '/category/summary', null);


insert into operation(opn_oid, opn_desc, module_oid) values(1, '进入欢迎页', 1);
insert into operation_url(opn_oid, opn_url) values(1, '/welcome');


insert into operation(opn_oid, opn_desc, module_oid) values(2, '进入消费模块', 2);
insert into operation_url(opn_oid, opn_url) values(2, '/consumption/summary');
insert into operation_url(opn_oid, opn_url) values(2, '/consumption/search');
insert into operation_url(opn_oid, opn_url) values(2, '/consumption/listOfSummary');

insert into operation(opn_oid, opn_desc, module_oid) values(3, '创建消费', 2);
insert into operation_url(opn_oid, opn_url) values(3, '/consumption/initAdd');
insert into operation_url(opn_oid, opn_url) values(3, '/consumption/confirmAdd');
insert into operation_url(opn_oid, opn_url) values(3, '/consumption/saveAdd');
insert into operation_url(opn_oid, opn_url) values(3, '/account/ajaxGetAllAccounts');
insert into operation_url(opn_oid, opn_url) values(3, '/category/ajaxGetAllCategories');

insert into operation(opn_oid, opn_desc, module_oid) values(4, '查看消费', 2);
insert into operation_url(opn_oid, opn_url) values(4, '/consumption/view');

insert into operation(opn_oid, opn_desc, module_oid) values(5, '更新消费', 2);
insert into operation_url(opn_oid, opn_url) values(5, '/consumption/initEdit');
insert into operation_url(opn_oid, opn_url) values(5, '/consumption/confirmEdit');
insert into operation_url(opn_oid, opn_url) values(5, '/consumption/saveEdit');
insert into operation_url(opn_oid, opn_url) values(5, '/account/ajaxGetAllAccounts');
insert into operation_url(opn_oid, opn_url) values(5, '/category/ajaxGetAllCategories');

insert into operation(opn_oid, opn_desc, module_oid) values(6, '删除消费', 2);
insert into operation_url(opn_oid, opn_url) values(6, '/consumption/delete');

insert into operation(opn_oid, opn_desc, module_oid) values(7, '确认消费', 2);
insert into operation_url(opn_oid, opn_url) values(7, '/consumption/confirm');

insert into operation(opn_oid, opn_desc, module_oid) values(8, '撤销消费', 2);
insert into operation_url(opn_oid, opn_url) values(8, '/consumption/rollback');


insert into operation(opn_oid, opn_desc, module_oid) values(9, '消费查询', 3);
insert into operation_url(opn_oid, opn_url) values(9, '/report/consumption');
insert into operation_url(opn_oid, opn_url) values(9, '/report/queryUserAmtConsumption');
insert into operation_url(opn_oid, opn_url) values(9, '/report/queryUserRatioConsumption');
insert into operation_url(opn_oid, opn_url) values(9, '/report/queryCategoryRatioConsumption');
insert into operation_url(opn_oid, opn_url) values(9, '/report/queryDetailConsumption');


insert into operation(opn_oid, opn_desc, module_oid) values(10, '进入收入模块', 4);
insert into operation_url(opn_oid, opn_url) values(10, '/incoming/summary');
insert into operation_url(opn_oid, opn_url) values(10, '/incoming/search');
insert into operation_url(opn_oid, opn_url) values(10, '/incoming/listOfSummary');

insert into operation(opn_oid, opn_desc, module_oid) values(11, '创建收入', 4);
insert into operation_url(opn_oid, opn_url) values(11, '/incoming/initAdd');
insert into operation_url(opn_oid, opn_url) values(11, '/incoming/confirmAdd');
insert into operation_url(opn_oid, opn_url) values(11, '/incoming/saveAdd');
insert into operation_url(opn_oid, opn_url) values(11, '/account/ajaxGetAllAccounts');

insert into operation(opn_oid, opn_desc, module_oid) values(12, '查看收入', 4);
insert into operation_url(opn_oid, opn_url) values(12, '/incoming/view');

insert into operation(opn_oid, opn_desc, module_oid) values(13, '更新收入', 4);
insert into operation_url(opn_oid, opn_url) values(13, '/incoming/initEdit');
insert into operation_url(opn_oid, opn_url) values(13, '/incoming/confirmEdit');
insert into operation_url(opn_oid, opn_url) values(13, '/incoming/saveEdit');
insert into operation_url(opn_oid, opn_url) values(13, '/account/ajaxGetAllAccounts');

insert into operation(opn_oid, opn_desc, module_oid) values(14, '删除收入', 4);
insert into operation_url(opn_oid, opn_url) values(14, '/incoming/delete');

insert into operation(opn_oid, opn_desc, module_oid) values(15, '确认收入', 4);
insert into operation_url(opn_oid, opn_url) values(15, '/incoming/confirm');

insert into operation(opn_oid, opn_desc, module_oid) values(16, '撤销收入', 4);
insert into operation_url(opn_oid, opn_url) values(16, '/incoming/rollback');


insert into operation(opn_oid, opn_desc, module_oid) values(17, '收入查询', 5);
insert into operation_url(opn_oid, opn_url) values(17, '/report/incoming');
insert into operation_url(opn_oid, opn_url) values(17, '/report/queryTotalIncoming');
insert into operation_url(opn_oid, opn_url) values(17, '/report/queryTotalIncomingByType');
insert into operation_url(opn_oid, opn_url) values(17, '/report/queryIncomingByMonth');


insert into operation(opn_oid, opn_desc, module_oid) values(18, '进入账户模块', 6);
insert into operation_url(opn_oid, opn_url) values(18, '/account/summary');
insert into operation_url(opn_oid, opn_url) values(18, '/account/search');
insert into operation_url(opn_oid, opn_url) values(18, '/account/listOfSummary');
insert into operation_url(opn_oid, opn_url) values(18, '/account/alaxGetAllAccountsByUser');

insert into operation(opn_oid, opn_desc, module_oid) values(19, '创建账户', 6);
insert into operation_url(opn_oid, opn_url) values(19, '/account/initAdd');
insert into operation_url(opn_oid, opn_url) values(19, '/account/confirmAdd');
insert into operation_url(opn_oid, opn_url) values(19, '/account/saveAdd');

insert into operation(opn_oid, opn_desc, module_oid) values(20, '查看账户', 6);
insert into operation_url(opn_oid, opn_url) values(20, '/account/view');
insert into operation_url(opn_oid, opn_url) values(20, '/account/listOfItemSummary');

insert into operation(opn_oid, opn_desc, module_oid) values(21, '更新账户', 6);
insert into operation_url(opn_oid, opn_url) values(21, '/account/initEdit');
insert into operation_url(opn_oid, opn_url) values(21, '/account/confirmEdit');
insert into operation_url(opn_oid, opn_url) values(21, '/account/saveEdit');

insert into operation(opn_oid, opn_desc, module_oid) values(22, '删除账户', 6);
insert into operation_url(opn_oid, opn_url) values(22, '/account/delete');

insert into operation(opn_oid, opn_desc, module_oid) values(23, '账户转账', 6);
insert into operation_url(opn_oid, opn_url) values(23, '/account/initTransfer');
insert into operation_url(opn_oid, opn_url) values(23, '/account/confirmTransfer');
insert into operation_url(opn_oid, opn_url) values(23, '/account/saveTransfer');


insert into operation(opn_oid, opn_desc, module_oid) values(24, '进入类别模块', 7);
insert into operation_url(opn_oid, opn_url) values(24, '/category/summary');
insert into operation_url(opn_oid, opn_url) values(24, '/category/ajaxGetAllCategories');
insert into operation_url(opn_oid, opn_url) values(24, '/category/ajaxGetCategoriesByParent');

insert into operation(opn_oid, opn_desc, module_oid) values(25, '创建类别', 7);
insert into operation_url(opn_oid, opn_url) values(25, '/category/initAdd');
insert into operation_url(opn_oid, opn_url) values(25, '/category/confirmAdd');
insert into operation_url(opn_oid, opn_url) values(25, '/category/saveAdd');

insert into operation(opn_oid, opn_desc, module_oid) values(26, '查看类别', 7);
insert into operation_url(opn_oid, opn_url) values(26, '/category/view');

insert into operation(opn_oid, opn_desc, module_oid) values(27, '更新类别', 7);
insert into operation_url(opn_oid, opn_url) values(27, '/category/initEdit');
insert into operation_url(opn_oid, opn_url) values(27, '/category/confirmEdit');
insert into operation_url(opn_oid, opn_url) values(27, '/category/saveEdit');

insert into operation(opn_oid, opn_desc, module_oid) values(28, '删除类别', 7);
insert into operation_url(opn_oid, opn_url) values(28, '/category/delete');

insert into user_type_operation(user_type_oid, opn_oid) values(1, 1);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 2);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 3);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 4);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 5);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 6);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 7);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 8);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 9);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 10);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 11);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 12);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 13);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 14);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 15);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 16);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 17);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 18);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 19);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 20);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 21);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 22);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 23);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 24);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 25);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 26);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 27);
insert into user_type_operation(user_type_oid, opn_oid) values(1, 28);



insert into role_operation(role_oid, opn_oid) values(1, 1);
insert into role_operation(role_oid, opn_oid) values(1, 2);
insert into role_operation(role_oid, opn_oid) values(1, 3);
insert into role_operation(role_oid, opn_oid) values(1, 4);
insert into role_operation(role_oid, opn_oid) values(1, 5);
insert into role_operation(role_oid, opn_oid) values(1, 6);
insert into role_operation(role_oid, opn_oid) values(1, 7);
insert into role_operation(role_oid, opn_oid) values(1, 8);
insert into role_operation(role_oid, opn_oid) values(1, 9);
insert into role_operation(role_oid, opn_oid) values(1, 10);
insert into role_operation(role_oid, opn_oid) values(1, 11);
insert into role_operation(role_oid, opn_oid) values(1, 12);
insert into role_operation(role_oid, opn_oid) values(1, 13);
insert into role_operation(role_oid, opn_oid) values(1, 14);
insert into role_operation(role_oid, opn_oid) values(1, 15);
insert into role_operation(role_oid, opn_oid) values(1, 16);
insert into role_operation(role_oid, opn_oid) values(1, 17);
insert into role_operation(role_oid, opn_oid) values(1, 18);
insert into role_operation(role_oid, opn_oid) values(1, 19);
insert into role_operation(role_oid, opn_oid) values(1, 20);
insert into role_operation(role_oid, opn_oid) values(1, 21);
insert into role_operation(role_oid, opn_oid) values(1, 22);
insert into role_operation(role_oid, opn_oid) values(1, 23);
insert into role_operation(role_oid, opn_oid) values(1, 24);
insert into role_operation(role_oid, opn_oid) values(1, 25);
insert into role_operation(role_oid, opn_oid) values(1, 26);
insert into role_operation(role_oid, opn_oid) values(1, 27);
insert into role_operation(role_oid, opn_oid) values(1, 28);

insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(100, '日常消费', 4300, 0, false, null, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(101, '食品', 1000, 1, true, 100, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(102, '酒水', 600, 1, true, 100, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(103, '水电', 500, 1, true, 100, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(104, '水果', 200, 1, true, 100, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(105, '通讯', 200, 1, true, 100, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(106, '交通', 200, 1, true, 100, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(107, '服装', 1000, 1, true, 100, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(108, '医药', 200, 1, true, 100, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(109, '零食', 200, 1, true, 100, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(110, '其它', 200, 1, true, 100, now(), '初始化数据');

insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(200, '育儿', 1600, 0, false, null, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(201, '玩具', 100, 1, true, 200, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(202, '学费', 200, 1, true, 200, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(203, '书籍', 100, 1, true, 200, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(204, '服装', 200, 1, true, 200, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(205, '奶粉', 800, 1, true, 200, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(206, '其它', 200, 1, true, 200, now(), '初始化数据');

insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(300, '房产', 7600, 0, false, null, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(301, '购房', 1000, 1, true, 300, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(302, '房租', 100, 1, true, 300, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(303, '装修', 500, 1, true, 300, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(304, '贷款', 6000, 1, true, 300, now(), '初始化数据');

insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(400, '汽车', 2000, 0, false, null, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(401, '油费', 700, 1, true, 400, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(402, '停车费', 300, 1, true, 400, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(403, '保险', 500, 1, true, 400, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(404, '保养', 500, 1, true, 400, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(405, '购车', 0, 1, true, 400, now(), '初始化数据');

insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(500, '投资', 500, 0, false, null, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(501, '彩票', 50, 1, true, 500, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(502, '书刊', 100, 1, true, 500, now(), '初始化数据');

insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(600, '健身', 300, 0, false, null, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(601, '器材', 100, 1, true, 600, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(602, '场地费', 100, 1, true, 600, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(603, '运动', 100, 1, true, 600, now(), '初始化数据');

insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(700, '人际', 300, 0, false, null, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(701, '礼金', 100, 1, true, 700, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(702, '请客', 200, 1, true, 700, now(), '初始化数据');

insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(800, '数码电器', 950, 0, false, null, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(801, '家用电器', 500, 1, true, 800, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(802, '厨房电器', 200, 1, true, 800, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(803, '电脑', 100, 1, true, 800, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(804, '手机', 100, 1, true, 800, now(), '初始化数据');
insert into category(category_oid, category_desc, monthly_budget ,category_level, is_leaf, parent_oid, create_time, create_by)
values(805, '其它', 50, 1, true, 800, now(), '初始化数据');

