CREATE TABLE room (
                      kdno INT,
                      kcno INT,
                      ccno INT,
                      kdname VARCHAR(255),
                      exptime TIMESTAMP,
                      papername VARCHAR(255),
                      PRIMARY KEY (kdno, kcno, ccno)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
