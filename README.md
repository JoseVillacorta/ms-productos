# MS Productos

Microservicio de productos reactivo - Versión básica.

## Descripción
MS Productos es un microservicio reactivo básico para gestión de productos:

- **Arquitectura Reactiva**: Spring WebFlux para operaciones no bloqueantes
- **Base de Datos Reactiva**: R2DBC con PostgreSQL
- **CRUD Básico**: Crear, leer, actualizar y eliminar productos
- **Gestión de Stock**: Control básico de inventario
- **Service Discovery**: Registro en Eureka

**Nota**: Esta es la versión básica. Para funcionalidades avanzadas (Kafka, eventos), ver `ms-productos-v2`.

## Funcionalidades

### CRUD de Productos
- **Crear**: Registro de productos
- **Consultar**: Búsqueda por ID y listado
- **Actualizar**: Modificación de productos y stock
- **Eliminar**: Eliminación de productos

### Gestión de Inventario
- **Stock Control**: Actualización de inventario
- **Bajo Stock**: Consulta de productos con stock bajo

## Dependencias

### Base de Datos
- **PostgreSQL**: Base de datos reactiva
- **Base de datos**: `db_productos_dev`, `db_productos_qa`, `db_productos_prod`
- **Script**: Ejecutar `database/script.sql`

### Servicios
- **Registry Service**: http://localhost:8761
- **Config Server**: http://localhost:8888

## Ejecutar

### Desarrollo Local
```bash
./gradlew bootRun --spring.profiles.active=dev
```
