CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(50) NOT NULL,
                       name VARCHAR(100),
                       age INT,
                       gender CHAR(1),  -- 'M' 代表男性，'F' 代表女性
                       phone VARCHAR(15)
);

-- 如果需要，可以在这里添加更多的约束和索引，例如：
-- CREATE INDEX idx_username ON users (username);