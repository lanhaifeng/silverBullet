create table DATA_OPERATE_LOGS (
  ID           VARCHAR2(64),
  OPERATE_TYPE VARCHAR2(100),
  OPERATE_DATE DATE,
  DATA_TYPE    VARCHAR2(100),
  DATA_ID      NUMBER(10),
  CREATE_DATE  DATE,
  CREATE_USER  VARCHAR2(100),
  UPDATE_DATE  DATE,
  UPDATE_USER  VARCHAR2(100)
);