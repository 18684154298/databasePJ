CREATE TABLE sell (
                      Commodity_ID INT PRIMARY KEY,
                      Vendor_ID INT,
                      FOREIGN KEY (Commodity_ID) REFERENCES Commodity(ID) ON DELETE CASCADE,
                      FOREIGN KEY (Vendor_ID) REFERENCES vendor(ID) ON DELETE CASCADE
);
