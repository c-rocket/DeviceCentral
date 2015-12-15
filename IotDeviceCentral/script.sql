DROP TABLE iot_device;
CREATE TABLE iot_device
(
    ID       NUMBER,
    NAME     VARCHAR2(60) UNIQUE NOT NULL,
    device CLOB,
    picture CLOB
);
  
DROP SEQUENCE iot_device_seq;
  
CREATE SEQUENCE iot_device_seq
MINVALUE 1
MAXVALUE 999999999999999999999999999
START WITH 1
INCREMENT BY 1
CACHE 20;

ALTER TABLE iot_device
  ADD industry VARCHAR2(60) DEFAULT 'General' NOT NULL;
ALTER TABLE iot_device
  ADD download_count NUMBER DEFAULT 0 NOT NULL;
ALTER TABLE iot_device
  ADD rating NUMBER;
ALTER TABLE iot_device
  ADD rating_count NUMBER;
UPDATE IOT_DEVICE SET rating_count = 0;
UPDATE IOT_DEVICE SET rating = 0;

ALTER TABLE iot_device MODIFY rating DEFAULT 0;
ALTER TABLE iot_device MODIFY rating_count DEFAULT 0;
