
CREATE DATABASE IF NOT EXISTS biblioteca CHARACTER SET utf8mb4;

CREATE USER IF NOT EXISTS 'biblioteca'@'localhost' IDENTIFIED BY 'biblioteca123';

GRANT ALL PRIVILEGES ON biblioteca.* TO 'biblioteca'@'localhost';

FLUSH PRIVILEGES;
