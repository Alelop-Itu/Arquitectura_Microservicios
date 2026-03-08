CREATE TABLE customers
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(100)       NOT NULL,
    gender         VARCHAR(20),
    identification VARCHAR(20) UNIQUE NOT NULL,
    address        VARCHAR(255),
    phone          VARCHAR(20),
    password       VARCHAR(255)       NOT NULL,
    status         BOOLEAN DEFAULT TRUE
);


CREATE TABLE accounts
(
    id             SERIAL PRIMARY KEY,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    type           VARCHAR(50), -- Ahorro / Corriente
    balance        DECIMAL(15, 2)     NOT NULL,
    status         BOOLEAN DEFAULT TRUE,
    customer_id    INTEGER REFERENCES customers (id) ON DELETE CASCADE
);


CREATE TABLE movements
(
    id         SERIAL PRIMARY KEY,
    date       TIMESTAMP      NOT NULL,
    type       VARCHAR(20)    NOT NULL, -- DEBIT / CREDIT
    value      DECIMAL(15, 2) NOT NULL, -- Cambiado de movement_value a value
    balance    DECIMAL(15, 2) NOT NULL, -- Saldo disponible después del movimiento
    account_id INTEGER REFERENCES accounts (id) ON DELETE CASCADE
);