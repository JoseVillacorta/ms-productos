# Microservicio de Productos

Gestión de catálogo de productos con operaciones CRUD completas.

## Funcionalidades

- **CRUD productos**: Crear, leer, actualizar, eliminar productos
- **Control de stock**: Incremento/decremento automático
- **Base de datos reactiva**: R2DBC con PostgreSQL
- **API REST**: Endpoints funcionales con WebFlux
- **Validación de stock**: Prevención de ventas sin inventario

## Endpoints

- `GET /products` → Listar todos los productos
- `GET /products/{id}` → Obtener producto específico
- `POST /products` → Crear nuevo producto
- `PUT /products/{id}` → Actualizar producto
- `PUT /products/{id}/stock` → Actualizar stock
- `GET /products/bajo-stock` → Productos con stock bajo
- `DELETE /products/{id}` → Eliminar producto

## Configuración Docker

- **Puerto**: 8081
- **Base de datos**: `host.docker.internal:5432` (PostgreSQL local)
- **Perfil**: docker
- **Eureka**: `registry-service:8761`

## Modelo de Datos

```sql
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL
);
```

## Despliegue

```bash
docker-compose up --build ms-productos
```

## Health Check

- Endpoint: `http://localhost:8081/actuator/health`
- Estado esperado: `{"status":"UP"}`

## Notas

- Requiere PostgreSQL corriendo en puerto 5432 local
- Seguridad configurada para permitir `/products/**` y `/actuator/**`
- Compatible con CORS para desarrollo frontend
