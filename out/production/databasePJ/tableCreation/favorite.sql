CREATE TABLE favorite (
                          cu_id INT NOT NULL,
                          co_id INT NOT NULL,
                          threshold DECIMAL(10, 2) NOT NULL,
                          PRIMARY KEY (cu_id, co_id),
                          FOREIGN KEY (cu_id) REFERENCES customer(id)ON DELETE CASCADE,
                          FOREIGN KEY (co_id) REFERENCES Commodity(ID)ON DELETE CASCADE
);
