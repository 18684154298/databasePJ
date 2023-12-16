CREATE TABLE notice (
                        n_id INT NOT NULL,
                        c_id INT NOT NULL,
                        PRIMARY KEY (n_id, c_id),
                        FOREIGN KEY (n_id) REFERENCES notification(ID)ON DELETE CASCADE,
                        FOREIGN KEY (c_id) REFERENCES customer(id)ON DELETE CASCADE
);
