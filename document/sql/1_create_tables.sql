-- 
-- table: account
--

create table account(
    acnt_oid                    bigint          not null         auto_increment,
    acnt_desc                   nvarchar(30)    not null,
    acnt_type                   enum('cash', 'bankcard', 'creditcard', 'alipay', 'epp', 'accumulation', 'medicalinsurance', 'fund', 'other' )  not null,
    balance                     numeric(15,2)   not null,
    quota                       numeric(15,2),
    debt                        numeric(15,2),
    owner_oid                   bigint          not null,
    disabled                    bit             not null,
    create_time                 datetime        not null,
    update_time                 datetime,
    create_by                   nvarchar(6)     not null,
    update_by                   nvarchar(6),
    seq_no                      int             not null        default 1,
    primary key (acnt_oid)
)engine=innodb;



-- 
-- table: category
--

create table category(
    category_oid                bigint          not null        auto_increment,
    category_desc               nvarchar(10)    not null,
    monthly_budget              numeric(15,2)   not null,
    category_level              smallint        not null,
    is_leaf                     boolean         not null,
    parent_oid                  bigint,
    create_time                 datetime        not null,
    update_time                 datetime,
    create_by                   nvarchar(6)     not null,
    update_by                   nvarchar(6),
    seq_no                      int             not null        default 1,
    primary key (category_oid)
)engine=innodb;



-- 
-- table: consumption
--

create table consumption(
    cpn_oid                     bigint          not null        auto_increment,
    cpn_type                    enum('supermarket', 'online', 'store', 'other')     not null,
    amount                      numeric(15,2)   not null,
    cpn_time                    datetime        not null,
    batch_num                   char(32)        not null,
    confirmed                   boolean         not null,
    create_time                 datetime        not null,
    update_time                 datetime,
    create_by                   nvarchar(6)     not null,
    update_by                   nvarchar(6),
    seq_no                      int             not null        default 1,
    primary key (cpn_oid)
)engine=innodb;



-- 
-- table: consumption_item
--

create table consumption_item(
    item_oid                    bigint          not null        auto_increment,
    item_desc                   nvarchar(30)    not null,
    amount                      numeric(15,2)   not null,
    owner_oid                   bigint          not null,
    cpn_oid                     bigint          not null,
    category_oid                bigint          not null,
    primary key (item_oid)
)engine=innodb;



-- 
-- table: account_consumption
--

create table account_consumption(
    acnt_oid                    bigint          not null,
    cpn_oid                     bigint          not null,
    amount                      numeric(15,2)   not null,
    primary key (acnt_oid, cpn_oid)
)engine=innodb;



-- 
-- table: account_audit
--

create table account_audit(
    adt_oid                     bigint          not null        auto_increment,
    adt_desc                    nvarchar(512)   not null,
    adt_type                    enum('add', 'subtract', 'change', 'trans_add', 'trans_subtract', 'rollback')   not null,
    adt_time                    datetime        not null,
    balance_after               numeric(10,2)   not null,
    chg_amt                     numeric(10,2)   not null,
    acnt_oid                    bigint          not null,
    batch_num                   char(32)        not null,
    create_time                 datetime        not null,
    create_by                   nvarchar(6)     not null,
    primary key (adt_oid)
)engine=innodb;



-- 
-- table: incoming
--

create table incoming(
    incoming_oid                bigint          not null        auto_increment,
    incoming_desc               nvarchar(30)    not null,
    amount                      numeric(15,2)   not null,
    incoming_type               enum('salary', 'bonus', 'cash', 'investment', 'accumulation', 'other')    not null,
    confirmed                   boolean         not null,
    owner_oid                   bigint          not null,
    incoming_date               date            not null,
    batch_num                   char(32)        not null,
    create_time                 datetime        not null,
    update_time                 datetime,
    create_by                   nvarchar(6)     not null,
    update_by                   nvarchar(6),
    seq_no                      int             not null        default 1,
    primary key (incoming_oid)
)engine=innodb;



-- 
-- table: account_incoming
--

create table account_incoming(
    acnt_oid                    bigint          not null,
    incoming_oid                bigint          not null,
    primary key (acnt_oid, incoming_oid)
)engine=innodb;



-- 
-- table: user_profile
--

create table user_profile(
    user_oid                    bigint          not null        auto_increment,
    user_name                   nvarchar(6)     not null,
    user_alias                  nvarchar(10)    not null,
    gender                      enum('male', 'female')  not null,
    phone                       varchar(11)     not null,
    email                       varchar(50)     not null,
    icon                        varchar(50)     not null,
    remarks                     varchar(128)    not null,
    login_id                    varchar(10)     not null,
    login_pwd                   varchar(128)    not null,
    user_type_oid               bigint          not null,
    create_time                 datetime        not null,
    update_time                 datetime,
    create_by                   nvarchar(6)     not null,
    update_by                   nvarchar(6),
    seq_no                      int             not null        default 1,
    primary key (user_oid)
)engine=innodb;



-- 
-- table: user_type
--

create table user_type(
    user_type_oid               bigint          not null,
    user_type_desc              nvarchar(10)    not null,
    primary key (user_type_oid)
)engine=innodb;



-- 
-- table: role_profile
--

create table role_profile(
    role_oid                    bigint          not null        auto_increment,
    role_desc                   nvarchar(10)    not null,
    user_type_oid               bigint          not null,
    primary key (role_oid)
)engine=innodb;



-- 
-- table: user_role
--

create table user_role(
    user_oid                    bigint          not null,
    role_oid                    bigint          not null,
    primary key (user_oid, role_oid)
)engine=innodb;



-- 
-- table: module
--

create table module(
    module_oid                  bigint          not null,
    module_desc                 nvarchar(10)    not null,
    module_level                smallint        not null,
    show_order                  smallint        not null,
    module_link                 varchar(50),
    parent_oid                  bigint,
    primary key (module_oid)
)engine=innodb;



-- 
-- table: operation
--

create table operation(
    opn_oid                     bigint          not null,
    opn_desc                    nvarchar(10)    not null,
    module_oid                  bigint          not null,
    primary key (opn_oid)
)engine=innodb;



-- 
-- table: operation_url
--

create table operation_url(
    opn_oid                     bigint          not null,
    opn_url                     varchar(50)     not null,
    primary key (opn_oid, opn_url)
)engine=innodb;



-- 
-- table: role_operation
--

create table role_operation(
    role_oid                    bigint          not null,
    opn_oid                     bigint          not null,
    primary key (role_oid, opn_oid)
)engine=innodb;



-- 
-- table: user_type_operation
--

create table user_type_operation(
    user_type_oid               bigint          not null,
    opn_oid                     bigint          not null,
    primary key (user_type_oid, opn_oid)
)engine=innodb;
