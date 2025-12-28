-- BaseDatos.sql
-- DDL for microservice banking scenario, MySQL compatible
-- Includes schema, tables, constraints, basic triggers, and helper views for reporting

CREATE SCHEMA IF NOT EXISTS microservice_bank DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE microservice_bank;

-- Person entity holds shared attributes for clients and potential future profiles
DROP TABLE IF EXISTS person;
CREATE TABLE person (
    person_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    gender ENUM('MALE','FEMALE','OTHER') NOT NULL DEFAULT 'OTHER',
    age TINYINT UNSIGNED CHECK (age BETWEEN 0 AND 120),
    identification VARCHAR(60) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Client inherits from person and contains credentials and status information
DROP TABLE IF EXISTS client;
CREATE TABLE client (
    client_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    person_id BIGINT UNSIGNED NOT NULL UNIQUE,
    client_code CHAR(10) NOT NULL UNIQUE,
    password_hash CHAR(60) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (person_id) REFERENCES person(person_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Account entity linked to client, storing balance and status
DROP TABLE IF EXISTS account;
CREATE TABLE account (
    account_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(32) NOT NULL UNIQUE,
    client_id BIGINT UNSIGNED NOT NULL,
    account_type ENUM('SAVINGS','CHECKING','CURRENT') NOT NULL DEFAULT 'SAVINGS',
    initial_balance DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    current_balance DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES client(client_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_account_client (client_id)
) ENGINE=InnoDB;

-- Movement entity capturing account transactions with resulting balance
DROP TABLE IF EXISTS movement;
CREATE TABLE movement (
    movement_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT UNSIGNED NOT NULL,
    movement_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    movement_type ENUM('DEBIT','CREDIT') NOT NULL,
    description VARCHAR(255) DEFAULT '',
    amount DECIMAL(18,2) NOT NULL,
    balance_after DECIMAL(18,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_movement_account (account_id)
) ENGINE=InnoDB;

-- Movement events table for asynchronous messaging between microservices
DROP TABLE IF EXISTS movement_event;
CREATE TABLE movement_event (
    event_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    movement_id BIGINT UNSIGNED NOT NULL,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    routed_at TIMESTAMP NULL DEFAULT NULL,
    payload JSON NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (movement_id) REFERENCES movement(movement_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- View to support account statement reporting
DROP VIEW IF EXISTS account_statement;
CREATE VIEW account_statement AS
SELECT 
    c.client_code,
    p.first_name,
    p.last_name,
    a.account_number,
    a.account_type,
    a.current_balance,
    m.movement_date,
    m.movement_type,
    m.amount AS movement_amount,
    m.balance_after,
    m.description
FROM client c
JOIN person p ON c.person_id = p.person_id
JOIN account a ON a.client_id = c.client_id
LEFT JOIN movement m ON m.account_id = a.account_id;

-- Movement data handling now occurs inside the Java application; database triggers are retired.

-- Movement registration is delegated to the Java service; stored procedures are no longer required.

-- Seed data for reference
INSERT INTO person (first_name, last_name, gender, age, identification, address, phone)
VALUES
('Jose', 'Lema', 'MALE', 34, '0102030405', 'Otavalo sn y principal', '098254785'),
('Marianela', 'Montalvo', 'FEMALE', 29, '0102030406', 'Amazonas y NNUU', '097548965'),
('Juan', 'Osorio', 'MALE', 31, '0102030407', '13 junio y Equinoccial', '098874587');

INSERT INTO client (person_id, client_code, password_hash)
SELECT person_id, CONCAT('CL', LPAD(person_id, 6, '0')), 'changeme'
FROM person;

INSERT INTO account (account_number, client_id, account_type, initial_balance, current_balance)
VALUES
('478758', 1, 'SAVINGS', 2000.00, 2000.00),
('225487', 2, 'CURRENT', 100.00, 100.00),
('495878', 3, 'SAVINGS', 0.00, 0.00),
('496825', 2, 'SAVINGS', 540.00, 540.00);

-- Simple movement entries to reflect sample operations
INSERT INTO movement (account_id, movement_type, description, amount, balance_after)
VALUES
    (1, 'DEBIT', 'Retiro', -575.00, 1425.00),
    (2, 'CREDIT', 'Deposito', 600.00, 700.00),
    (3, 'CREDIT', 'Deposito', 150.00, 150.00),
    (4, 'DEBIT', 'Retiro', -540.00, 0.00);

-- Additional movement example
INSERT INTO account (account_number, client_id, account_type, initial_balance, current_balance)
VALUES ('585545', 1, 'CURRENT', 1000.00, 1000.00);

COMMIT;
