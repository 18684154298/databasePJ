CREATE TABLE Platform (
                          ID INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO Platform (name) VALUES ('淘宝');
INSERT INTO Platform (name) VALUES ('得物');
INSERT INTO Platform (name) VALUES ('拼多多');
INSERT INTO Platform (name) VALUES ('京东');
INSERT INTO Platform (name) VALUES ('闲鱼');
