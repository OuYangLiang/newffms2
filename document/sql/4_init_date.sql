INSERT INTO USER_TYPE(USER_TYPE_OID, USER_TYPE_DESC) VALUES (1, '普通用户');

INSERT INTO USER_PROFILE(USER_OID, USER_NAME, USER_ALIAS, GENDER, PHONE, EMAIL, ICON, REMARKS, LOGIN_ID, LOGIN_PWD, USER_TYPE_OID, CREATE_TIME, CREATE_BY)
VALUES(1, '欧阳亮', '老公', 'Male', '18652022500', 'ouyanggod@gmail.com', 'oyl.jpg', '苏宁：高级架构师', 'oyl', '', 1, now(), '初始化数据');
INSERT INTO USER_PROFILE(USER_OID, USER_NAME, USER_ALIAS, GENDER, PHONE, EMAIL, ICON, REMARKS, LOGIN_ID, LOGIN_PWD, USER_TYPE_OID, CREATE_TIME, CREATE_BY)
VALUES(2, '喻敏', '老婆', 'Female', '18652930160', 'yumingirl@gmail.com', 'ym.jpg', '中兴：测试工程师', 'yumin', '', 1, now(), '初始化数据');
INSERT INTO USER_PROFILE(USER_OID, USER_NAME, USER_ALIAS, GENDER, PHONE, EMAIL, ICON, REMARKS, LOGIN_ID, LOGIN_PWD, USER_TYPE_OID, CREATE_TIME, CREATE_BY)
VALUES(3, '欧阳晓筱', '宝贝女儿', 'Female', '', '', 'oyxx.jpg', '小可爱', 'oyxx', '', 1, now(), '初始化数据');
UPDATE USER_PROFILE SET LOGIN_PWD = '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2';

INSERT INTO ROLE_PROFILE(ROLE_OID, ROLE_DESC, USER_TYPE_OID) VALUES (1, '普通用户', 1);

INSERT INTO USER_ROLE(USER_OID, ROLE_OID) VALUES (1, 1);
INSERT INTO USER_ROLE(USER_OID, ROLE_OID) VALUES (2, 1);
INSERT INTO USER_ROLE(USER_OID, ROLE_OID) VALUES (3, 1);

INSERT INTO MODULE(MODULE_OID, MODULE_DESC, MODULE_LEVEL, SHOW_ORDER, MODULE_LINK, PARENT_OID)
VALUES(1, '首页', 1, 1, '/welcome', NULL);
INSERT INTO MODULE(MODULE_OID, MODULE_DESC, MODULE_LEVEL, SHOW_ORDER, MODULE_LINK, PARENT_OID)
VALUES(2, '消费', 1, 2, '/consumption/summary', NULL);
INSERT INTO MODULE(MODULE_OID, MODULE_DESC, MODULE_LEVEL, SHOW_ORDER, MODULE_LINK, PARENT_OID)
VALUES(3, '消费查询', 1, 3, '/report/consumption', NULL);
INSERT INTO MODULE(MODULE_OID, MODULE_DESC, MODULE_LEVEL, SHOW_ORDER, MODULE_LINK, PARENT_OID)
VALUES(4, '收入', 1, 4, '/incoming/summary', NULL);
INSERT INTO MODULE(MODULE_OID, MODULE_DESC, MODULE_LEVEL, SHOW_ORDER, MODULE_LINK, PARENT_OID)
VALUES(5, '收入查询', 1, 5, '/report/incoming', NULL);
INSERT INTO MODULE(MODULE_OID, MODULE_DESC, MODULE_LEVEL, SHOW_ORDER, MODULE_LINK, PARENT_OID)
VALUES(6, '账户', 1, 6, '/account/summary', NULL);
INSERT INTO MODULE(MODULE_OID, MODULE_DESC, MODULE_LEVEL, SHOW_ORDER, MODULE_LINK, PARENT_OID)
VALUES(7, '类别', 1, 7, '/category/summary', NULL);


INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(1, '进入欢迎页', 1);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(1, '/welcome');


INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(2, '进入消费模块', 2);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(2, '/consumption/summary');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(2, '/consumption/search');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(2, '/consumption/listOfSummary');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(3, '创建消费', 2);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(3, '/consumption/initAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(3, '/consumption/confirmAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(3, '/consumption/saveAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(3, '/account/ajaxGetAllAccounts');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(3, '/category/ajaxGetAllCategories');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(4, '查看消费', 2);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(4, '/consumption/view');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(5, '更新消费', 2);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(5, '/consumption/initEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(5, '/consumption/confirmEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(5, '/consumption/saveEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(5, '/account/ajaxGetAllAccounts');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(5, '/category/ajaxGetAllCategories');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(6, '删除消费', 2);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(6, '/consumption/delete');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(7, '确认消费', 2);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(7, '/consumption/confirm');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(8, '撤销消费', 2);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(8, '/consumption/rollback');


INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(9, '消费查询', 3);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(9, '/report/consumption');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(9, '/report/queryUserAmtConsumption');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(9, '/report/queryUserRatioConsumption');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(9, '/report/queryCategoryRatioConsumption');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(9, '/report/queryDetailConsumption');


INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(10, '进入收入模块', 4);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(10, '/incoming/summary');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(10, '/incoming/search');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(10, '/incoming/listOfSummary');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(11, '创建收入', 4);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(11, '/incoming/initAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(11, '/incoming/confirmAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(11, '/incoming/saveAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(11, '/account/ajaxGetAllAccounts');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(12, '查看收入', 4);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(12, '/incoming/view');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(13, '更新收入', 4);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(13, '/incoming/initEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(13, '/incoming/confirmEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(13, '/incoming/saveEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(13, '/account/ajaxGetAllAccounts');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(14, '删除收入', 4);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(14, '/incoming/delete');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(15, '确认收入', 4);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(15, '/incoming/confirm');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(16, '撤销收入', 4);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(16, '/incoming/rollback');


INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(17, '收入查询', 5);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(17, '/report/incoming');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(17, '/report/queryTotalIncoming');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(17, '/report/queryTotalIncomingByType');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(17, '/report/queryIncomingByMonth');


INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(18, '进入账户模块', 6);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(18, '/account/summary');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(18, '/account/search');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(18, '/account/listOfSummary');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(18, '/account/alaxGetAllAccountsByUser');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(19, '创建账户', 6);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(19, '/account/initAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(19, '/account/confirmAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(19, '/account/saveAdd');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(20, '查看账户', 6);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(20, '/account/view');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(20, '/account/listOfItemSummary');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(21, '更新账户', 6);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(21, '/account/initEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(21, '/account/confirmEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(21, '/account/saveEdit');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(22, '删除账户', 6);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(22, '/account/delete');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(23, '账户转账', 6);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(23, '/account/initTransfer');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(23, '/account/confirmTransfer');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(23, '/account/saveTransfer');


INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(24, '进入类别模块', 7);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(24, '/category/summary');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(24, '/category/ajaxGetAllCategories');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(24, '/category/ajaxGetCategoriesByParent');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(25, '创建类别', 7);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(25, '/category/initAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(25, '/category/confirmAdd');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(25, '/category/saveAdd');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(26, '查看类别', 7);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(26, '/category/view');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(27, '更新类别', 7);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(27, '/category/initEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(27, '/category/confirmEdit');
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(27, '/category/saveEdit');

INSERT INTO OPERATION(OPN_OID, OPN_DESC, MODULE_OID) VALUES(28, '删除类别', 7);
INSERT INTO OPERATION_URL(OPN_OID, OPN_URL) VALUES(28, '/category/delete');

INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 1);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 2);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 3);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 4);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 5);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 6);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 7);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 8);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 9);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 10);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 11);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 12);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 13);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 14);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 15);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 16);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 17);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 18);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 19);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 20);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 21);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 22);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 23);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 24);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 25);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 26);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 27);
INSERT INTO USER_TYPE_OPERATION(USER_TYPE_OID, OPN_OID) VALUES(1, 28);



INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 1);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 2);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 3);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 4);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 5);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 6);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 7);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 8);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 9);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 10);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 11);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 12);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 13);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 14);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 15);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 16);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 17);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 18);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 19);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 20);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 21);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 22);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 23);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 24);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 25);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 26);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 27);
INSERT INTO ROLE_OPERATION(ROLE_OID, OPN_OID) VALUES(1, 28);

INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(100, '日常消费', 4300, 0, FALSE, NULL, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(101, '食品', 1000, 1, TRUE, 100, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(102, '酒水', 600, 1, TRUE, 100, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(103, '水电', 500, 1, TRUE, 100, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(104, '水果', 200, 1, TRUE, 100, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(105, '通讯', 200, 1, TRUE, 100, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(106, '交通', 200, 1, TRUE, 100, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(107, '服装', 1000, 1, TRUE, 100, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(108, '医药', 200, 1, TRUE, 100, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(109, '零食', 200, 1, TRUE, 100, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(110, '其它', 200, 1, TRUE, 100, now(), '初始化数据');

INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(200, '育儿', 1600, 0, FALSE, NULL, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(201, '玩具', 100, 1, TRUE, 200, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(202, '学费', 200, 1, TRUE, 200, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(203, '书籍', 100, 1, TRUE, 200, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(204, '服装', 200, 1, TRUE, 200, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(205, '奶粉', 800, 1, TRUE, 200, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(206, '其它', 200, 1, TRUE, 200, now(), '初始化数据');

INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(300, '房产', 7600, 0, FALSE, NULL, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(301, '购房', 1000, 1, TRUE, 300, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(302, '房租', 100, 1, TRUE, 300, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(303, '装修', 500, 1, TRUE, 300, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(304, '贷款', 6000, 1, TRUE, 300, now(), '初始化数据');

INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(400, '汽车', 2000, 0, FALSE, NULL, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(401, '油费', 700, 1, TRUE, 400, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(402, '停车费', 300, 1, TRUE, 400, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(403, '保险', 500, 1, TRUE, 400, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(404, '保养', 500, 1, TRUE, 400, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(405, '购车', 0, 1, TRUE, 400, now(), '初始化数据');

INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(500, '投资', 500, 0, FALSE, NULL, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(501, '彩票', 50, 1, TRUE, 500, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(502, '书刊', 100, 1, TRUE, 500, now(), '初始化数据');

INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(600, '健身', 300, 0, FALSE, NULL, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(601, '器材', 100, 1, TRUE, 600, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(602, '场地费', 100, 1, TRUE, 600, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(603, '运动', 100, 1, TRUE, 600, now(), '初始化数据');

INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(700, '人际', 300, 0, FALSE, NULL, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(701, '礼金', 100, 1, TRUE, 700, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(702, '请客', 200, 1, TRUE, 700, now(), '初始化数据');

INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(800, '数码电器', 950, 0, FALSE, NULL, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(801, '家用电器', 500, 1, TRUE, 800, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(802, '厨房电器', 200, 1, TRUE, 800, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(803, '电脑', 100, 1, TRUE, 800, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(804, '手机', 100, 1, TRUE, 800, now(), '初始化数据');
INSERT INTO CATEGORY(CATEGORY_OID, CATEGORY_DESC, MONTHLY_BUDGET ,CATEGORY_LEVEL, IS_LEAF, PARENT_OID, CREATE_TIME, CREATE_BY)
VALUES(805, '其它', 50, 1, TRUE, 800, now(), '初始化数据');
