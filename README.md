# 🏦 Bank API - Prueba Técnica (Junior)

API Reactiva para gestión de clientes, cuentas y movimientos bancarios.

## 🚀 Tecnologías

- **Java 17** & **Spring Boot 3.5**
- **Spring WebFlux** (Programación Reactiva)
- **Spring Data JPA** & **PostgreSQL**
- **Docker** & **Docker Compose**
- **OpenAPI / Swagger** para documentación

## Funcionalidades Implementadas
- **F1 (CRUD Clientes y Cuentas): Registro y mantenimiento de información básica.**
- **F2 (Movimientos): Registro de débitos y créditos con actualización automática de saldo.**
- **F3 (Manejo de Errores): Validación de saldo insuficiente con respuesta estructurada.**
- **Consulta de Historial: Endpoint para filtrar movimientos por rango de fechas y número de cuenta.**

## 🧪 Pruebas con Postman
He incluido una colección de pruebas en la carpeta `/postman` del proyecto.
1. Importa el archivo `Bank_API_Collection.json` en Postman.
2. Configura la variable `base_url` como `http://localhost:8080`.
3. Los endpoints base son:
    - **POST /api/v1/customers**
    - **POST /api/v1/accounts**
    - **POST /api/v1/movements**
    - **GET /api/v1/movements/filter?accountNumber=...&startDate=...&endDate=...**

## Frontend de Pruebas
He incluido una interfaz sencilla para visualizar el saldo. Una vez ejecutada la aplicación, accede a:
👉 http://localhost:8080/index.html

## 📖 Documentación de Endpoints
Swagger UI: http://localhost:8080/swagger-ui.html
Colección Postman: Ubicada en /docs/bank_collection.json.

## 🛠️ Ejecución rápida (Docker)
Para levantar todo el entorno (Base de Datos + API) con un solo comando:
```bash
docker-compose up --build

