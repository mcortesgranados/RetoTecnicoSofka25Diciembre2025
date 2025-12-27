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
    INDEX idx_movement_account (account_id),
    CHECK (amount >= 0.00)
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

-- Trigger to ensure account balances stay consistent when movements are inserted
DROP TRIGGER IF EXISTS movement_after_insert;
DELIMITER $$
CREATE TRIGGER movement_after_insert
AFTER INSERT ON movement
FOR EACH ROW
BEGIN
    DECLARE updated_balance DECIMAL(18,2);
    SET updated_balance = NEW.balance_after;
    UPDATE account
    SET current_balance = updated_balance,
        updated_at = CURRENT_TIMESTAMP
    WHERE account_id = NEW.account_id;

    INSERT INTO movement_event (movement_id, payload)
    VALUES (NEW.movement_id, JSON_OBJECT(
        'movement_id', NEW.movement_id,
        'account_id', NEW.account_id,
        'movement_type', NEW.movement_type,
        'amount', NEW.amount,
        'balance_after', NEW.balance_after,
        'movement_date', NEW.movement_date
    ));
END$$
DELIMITER ;

-- Stored procedure to register movement and enforce business rules
DROP PROCEDURE IF EXISTS register_movement;
DELIMITER $$
CREATE PROCEDURE register_movement(
    IN in_account_number VARCHAR(32),
    IN in_type ENUM('DEBIT','CREDIT'),
    IN in_amount DECIMAL(18,2),
    IN in_description VARCHAR(255)
)
BEGIN
    DECLARE acc_id BIGINT;
    DECLARE balance DECIMAL(18,2);

    SELECT account_id, current_balance INTO acc_id, balance
    FROM account
    WHERE account_number = in_account_number
      AND is_active = TRUE
    LIMIT 1;

    IF acc_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Account not found or inactive.';
    END IF;

    IF in_type = 'DEBIT' AND balance < in_amount THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Saldo no disponible';
    END IF;

    IF in_type = 'DEBIT' THEN
        SET balance = balance - in_amount;
    ELSE
        SET balance = balance + in_amount;
    END IF;

    INSERT INTO movement (account_id, movement_type, description, amount, balance_after)
    VALUES (acc_id, in_type, in_description, in_amount, balance);
END$$
DELIMITER ;

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
CALL register_movement('478758', 'DEBIT', 575.00, 'Retiro');
CALL register_movement('225487', 'CREDIT', 600.00, 'Deposito');
CALL register_movement('495878', 'CREDIT', 150.00, 'Deposito');
CALL register_movement('496825', 'DEBIT', 540.00, 'Retiro');

-- Additional movement example
INSERT INTO account (account_number, client_id, account_type, initial_balance, current_balance)
VALUES ('585545', 1, 'CURRENT', 1000.00, 1000.00);

COMMIT;
