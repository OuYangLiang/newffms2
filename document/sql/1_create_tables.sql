-- 
-- TABLE: ACCOUNT
--

CREATE TABLE ACCOUNT(
    ACNT_OID                    BIGINT          NOT NULL         AUTO_INCREMENT,
    ACNT_DESC                   NVARCHAR(30)    NOT NULL,
    ACNT_TYPE                   ENUM('Cash', 'Bankcard', 'Creditcard', 'Alipay', 'Epp', 'Accumulation', 'MedicalInsurance', 'Other' )  NOT NULL,
    BALANCE                     NUMERIC(15,2)   NOT NULL,
    QUOTA                       NUMERIC(15,2),
    DEBT                        NUMERIC(15,2),
    OWNER_OID                   BIGINT          NOT NULL,
    CREATE_TIME                 DATETIME        NOT NULL,
    UPDATE_TIME                 DATETIME,
    CREATE_BY                   NVARCHAR(6)     NOT NULL,
    UPDATE_BY                   NVARCHAR(6),
    SEQ_NO                      INT             NOT NULL        DEFAULT 1,
    PRIMARY KEY (ACNT_OID)
)ENGINE=INNODB;



-- 
-- TABLE: CATEGORY
--

CREATE TABLE CATEGORY(
    CATEGORY_OID                BIGINT          NOT NULL        AUTO_INCREMENT,
    CATEGORY_DESC               NVARCHAR(10)    NOT NULL,
    MONTHLY_BUDGET              NUMERIC(15,2)   NOT NULL,
    CATEGORY_LEVEL              SMALLINT        NOT NULL,
    IS_LEAF                     BOOLEAN         NOT NULL,
    PARENT_OID                  BIGINT,
    CREATE_TIME                 DATETIME        NOT NULL,
    UPDATE_TIME                 DATETIME,
    CREATE_BY                   NVARCHAR(6)     NOT NULL,
    UPDATE_BY                   NVARCHAR(6),
    SEQ_NO                      INT             NOT NULL        DEFAULT 1,
    PRIMARY KEY (CATEGORY_OID)
)ENGINE=INNODB;



-- 
-- TABLE: CONSUMPTION
--

CREATE TABLE CONSUMPTION(
    CPN_OID                     BIGINT          NOT NULL        AUTO_INCREMENT,
    CPN_TYPE                    ENUM('Supermarket', 'Online', 'Store', 'Other')     NOT NULL,
    AMOUNT                      NUMERIC(15,2)   NOT NULL,
    CPN_TIME                    DATETIME        NOT NULL,
    BATCH_NUM                   CHAR(32)        NOT NULL,
    CONFIRMED                   BOOLEAN         NOT NULL,
    CREATE_TIME                 DATETIME        NOT NULL,
    UPDATE_TIME                 DATETIME,
    CREATE_BY                   NVARCHAR(6)     NOT NULL,
    UPDATE_BY                   NVARCHAR(6),
    SEQ_NO                      INT             NOT NULL        DEFAULT 1,
    PRIMARY KEY (CPN_OID)
)ENGINE=INNODB;



-- 
-- TABLE: CONSUMPTION_ITEM
--

CREATE TABLE CONSUMPTION_ITEM(
    ITEM_OID                    BIGINT          NOT NULL        AUTO_INCREMENT,
    ITEM_DESC                   NVARCHAR(30)    NOT NULL,
    AMOUNT                      NUMERIC(15,2)   NOT NULL,
    OWNER_OID                   BIGINT          NOT NULL,
    CPN_OID                     BIGINT          NOT NULL,
    CATEGORY_OID                BIGINT          NOT NULL,
    PRIMARY KEY (ITEM_OID)
)ENGINE=INNODB;



-- 
-- TABLE: ACCOUNT_CONSUMPTION
--

CREATE TABLE ACCOUNT_CONSUMPTION(
    ACNT_OID                    BIGINT          NOT NULL,
    CPN_OID                     BIGINT          NOT NULL,
    AMOUNT                      NUMERIC(15,2)   NOT NULL,
    PRIMARY KEY (ACNT_OID, CPN_OID)
)ENGINE=INNODB;



-- 
-- TABLE: ACCOUNT_AUDIT
--

CREATE TABLE ACCOUNT_AUDIT(
    ADT_OID                     BIGINT          NOT NULL        AUTO_INCREMENT,
    ADT_DESC                    NVARCHAR(512)   NOT NULL,
    ADT_TYPE                    ENUM('Add', 'Subtract', 'Change', 'Trans_add', 'Trans_subtract')   NOT NULL,
    ADT_TIME                    DATETIME        NOT NULL,
    BALANCE_AFTER               NUMERIC(10,2)   NOT NULL,
    CHG_AMT                     NUMERIC(10,2)   NOT NULL,
    ACNT_OID                    BIGINT          NOT NULL,
    BATCH_NUM                   CHAR(32)        NOT NULL,
    CREATE_TIME                 DATETIME        NOT NULL,
    CREATE_BY                   NVARCHAR(6)     NOT NULL,
    PRIMARY KEY (ADT_OID)
)ENGINE=INNODB;



-- 
-- TABLE: INCOMING
--

CREATE TABLE INCOMING(
    INCOMING_OID                BIGINT          NOT NULL        AUTO_INCREMENT,
    INCOMING_DESC               NVARCHAR(30)    NOT NULL,
    AMOUNT                      NUMERIC(15,2)   NOT NULL,
    INCOMING_TYPE               ENUM('Salary', 'Bonus', 'Cash', 'Investment', 'Accumulation', 'Other')    NOT NULL,
    CONFIRMED                   BOOLEAN         NOT NULL,
    OWNER_OID                   BIGINT          NOT NULL,
    INCOMING_DATE               DATE            NOT NULL,
    BATCH_NUM                   CHAR(32)        NOT NULL,
    CREATE_TIME                 DATETIME        NOT NULL,
    UPDATE_TIME                 DATETIME,
    CREATE_BY                   NVARCHAR(6)     NOT NULL,
    UPDATE_BY                   NVARCHAR(6),
    SEQ_NO                      INT             NOT NULL        DEFAULT 1,
    PRIMARY KEY (INCOMING_OID)
)ENGINE=INNODB;



-- 
-- TABLE: ACCOUNT_INCOMING
--

CREATE TABLE ACCOUNT_INCOMING(
    ACNT_OID                    BIGINT          NOT NULL,
    INCOMING_OID                BIGINT          NOT NULL,
    PRIMARY KEY (ACNT_OID, INCOMING_OID)
)ENGINE=INNODB;

