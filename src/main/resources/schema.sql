DROP TABLE IF EXISTS User;
CREATE TABLE User(
    username VARCHAR(50) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    password VARCHAR(300) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL
);

SELECT * FROM User WHERE username='abc' OR 1=1 #' AND password='ijSE@2023'  ;