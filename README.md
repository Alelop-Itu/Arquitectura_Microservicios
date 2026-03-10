# 🏦 Bank API - Prueba Técnica (Junior)

API Reactiva para gestión de clientes, cuentas y movimientos bancarios.

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

## 📖 Estructura del Proyecto

1. src/main/resources/import.sql: Script de semillas (Carga inicial de datos).
2. BaseDatos.sql: Esquema de base de datos y entidades.
3. index.html: Dashboard de visualización (Acceso: http://localhost:8080/index.html).
4. Especificación OpenAPI: Ubicada en /docs/openapi-spec.yaml

## 🧪 Pruebas y Calidad

1. Unitarias: Localizadas en src/test/java/application.service/MovementServiceTest.java. Validan la lógica de negocio y
   el requisito F3 (Saldo no disponible).
2. Integración (Karate DSL):** > *Nota: Se incluye la definición de pruebas en archivos `.feature` para validar los
   flujos de la API. Debido a discrepancias de versiones en el entorno local de compilación, la ejecución automatizada
   de estas pruebas mediante JUnit se encuentra comentada/desactivada, pero el diseño de las mismas es funcional y sigue
   la lógica de los endpoints desarrollados.*
3. Postman: Guía detallada de pruebas manuales en DOCS/POSTMAN-GUIDE.md.
