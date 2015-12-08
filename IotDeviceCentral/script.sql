DROP TABLE "iot_device";
CREATE TABLE iot_device
  (
    id       NUMBER,
    name     VARCHAR2(60) UNIQUE NOT NULL,
    device BLOB,
    picture BLOB
  );