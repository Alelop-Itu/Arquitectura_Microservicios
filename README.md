# 🏦 Bank API - Prueba Técnica (Junior)

API Reactiva para gestión de clientes, cuentas y movimientos bancarios desarrollada para el ITQ.

## 🚀 Tecnologías
- **Java 17** & **Spring Boot 3.5**
- **Spring WebFlux** (Programación Reactiva)
- **PostgreSQL 15** (Persistencia)
- **Docker & Docker Compose** (Contenerización)
- **OpenAPI / Swagger** & **Karate DSL** (Pruebas)

## 🛠️ Ejecución rápida con Docker
Para levantar la base de datos y la API simultáneamente:
1. Asegúrate de tener Docker abierto.
2. En la raíz del proyecto, ejecuta:
   ```bash
   mvn clean package -DskipTests
   docker-compose up --build

## 📖 Acceso a la Aplicación
1. Frontend de Pruebas: http://localhost:8080/index.html
2. Documentación Swagger: http://localhost:8080/swagger-ui.html
3. Especificación OpenAPI: Ubicada en /docs/openapi-spec.yaml

## 🧪 Pruebas Automatizadas
- Karate DSL: Los archivos .feature se encuentran en src/test/java.

- Postman: Guía detallada en DOCS/POSTMAN-GUIDE.md.
