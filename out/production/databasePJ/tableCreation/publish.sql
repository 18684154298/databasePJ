CREATE TABLE publish (
                         c_id INT NOT NULL,
                         p_id INT NOT NULL,
                         modified TINYINT NOT NULL DEFAULT 0,
                         PRIMARY KEY (c_id, p_id),
                         FOREIGN KEY (c_id) REFERENCES Commodity(ID)ON DELETE CASCADE,
                         FOREIGN KEY (p_id) REFERENCES Platform(ID)ON DELETE CASCADE
);