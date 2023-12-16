CREATE TABLE time_price (
                            c_id INT NOT NULL,
                            p_id INT NOT NULL,
                            price DECIMAL(10, 2) NOT NULL,
                            time DATETIME NOT NULL,
                            FOREIGN KEY (c_id, p_id) REFERENCES publish(c_id, p_id) ON DELETE CASCADE
);
