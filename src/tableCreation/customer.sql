CREATE TABLE customer (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       age INT NOT NULL,
                       gender CHAR(1) NOT NULL,  -- 'M' 代表男性，'F' 代表女性
                       phone VARCHAR(15) NOT NULL
);

-- 如果需要，可以在这里添加更多的约束和索引，例如：
-- CREATE INDEX idx_username ON users (username);