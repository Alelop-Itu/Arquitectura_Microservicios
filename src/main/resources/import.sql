-- Caso de Uso 1: Creación de Usuarios
INSERT INTO customers (id, name, identification, gender, address, phone, password, status) VALUES (1, 'Jose Lema', '123456', 'Masculino', 'Otavalo sn y principal', '098254785', '1234', true);
INSERT INTO customers (id, name, identification, gender, address, phone, password, status) VALUES (2, 'Marianela Montalvo', '789012', 'Femenino', 'Amazonas y NNUU', '097548965', '5678', true);
INSERT INTO customers (id, name, identification, gender, address, phone, password, status) VALUES (3, 'Juan Osorio', '345678', 'Masculino', '13 junio y Equinoccial', '098874587', '1245', true);

-- Caso de Uso 2 y 3: Creación de Cuentas de Usuario
INSERT INTO accounts (id, account_number, type, balance, status, customer_id) VALUES (1, '478758', 'Ahorro', 2000, true, 1);
INSERT INTO accounts (id, account_number, type, balance, status, customer_id) VALUES (2, '225487', 'Corriente', 100, true, 2);
INSERT INTO accounts (id, account_number, type, balance, status, customer_id) VALUES (3, '495878', 'Ahorro', 0, true, 3);
INSERT INTO accounts (id, account_number, type, balance, status, customer_id) VALUES (4, '496825', 'Ahorro', 540, true, 2);
INSERT INTO accounts (id, account_number, type, balance, status, customer_id) VALUES (5, '585545', 'Corriente', 1000, true, 1);

-- Caso de Uso 4 y 5: Realizar movimientos y saldos disponibles
-- Jose Lema: Retiro de 575 (Saldo inicial 2000 -> 1425)
INSERT INTO movements (id, date, type, value, balance, account_id) VALUES (1, '2022-02-10 09:00:00', 'Retiro', -575, 1425, 1);
-- Marianela Montalvo (Cta 225487): Depósito de 600 (Saldo inicial 100 -> 700)
INSERT INTO movements (id, date, type, value, balance, account_id) VALUES (2, '2022-02-10 10:00:00', 'Crédito', 600, 700, 2);
-- Juan Osorio: Depósito de 150 (Saldo inicial 0 -> 150)
INSERT INTO movements (id, date, type, value, balance, account_id) VALUES (3, '2022-02-08 11:00:00', 'Crédito', 150, 150, 3);
-- Marianela Montalvo (Cta 496825): Retiro de 540 (Saldo inicial 540 -> 0)
INSERT INTO movements (id, date, type, value, balance, account_id) VALUES (4, '2022-02-08 12:00:00', 'Débito', -540, 0, 4);etiro', -575, 1425, 1);