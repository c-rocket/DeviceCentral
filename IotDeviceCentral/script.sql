DROP TABLE iot_device;
CREATE TABLE iot_device
  (
    ID       NUMBER,
    NAME     VARCHAR2(60) UNIQUE NOT NULL,
    device CLOB,
    picture CLOB
  );