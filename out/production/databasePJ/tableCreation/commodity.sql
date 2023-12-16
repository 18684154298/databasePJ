CREATE TABLE Commodity (
                           ID INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           category VARCHAR(255) NOT NULL,
                           origin VARCHAR(255) NOT NULL,
                           MFD DATE NOT NULL
);