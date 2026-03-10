```markdown
# 🚀 Guía de Pruebas Manuales (Postman)

Base URL: `http://localhost:8080/api/v1`

### 1. Crear Cliente
**POST** `/customers`
```json
{
  "name": "Marianela Montalvo",
  "gender": "Femenino",
  "identification": "1234567890",
  "address": "Amazonas y NNUU",
  "phone": "0987654321",
  "password": "123",
  "status": true
}

2. Crear Cuenta
POST /accounts
Nota: Asegúrate de usar el ID del cliente creado.

{
  "number": "225487",
  "type": "Ahorros",
  "balance": 100.00,
  "status": true,
  "customerId": 1
}

3. Registrar Movimiento (Depósito de $600)
POST /movements

{
  "accountNumber": "225487",
  "type": "CREDIT",
  "value": 600.00
}

4. Consultar Reporte (Filtro)
GET /movements/filter?accountNumber=225487&startDate=2024-01-01T00:00:00&endDate=2026-12-31T23:59:59
GET /movements/filter?accountNumber=496825&startDate=2024-01-01T00:00:00&endDate=2026-12-31T23:59:59

5. Verificación General (Consultas GET)
Listar Clientes: GET /customers
Listar Cuentas: GET /accounts
Historial Completo: GET /movements
