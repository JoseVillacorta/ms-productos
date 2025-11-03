# MS Productos

Microservicio de gestión de productos del Sistema de Gestión de Pedidos.

## Descripción

API REST completa para gestión de productos con base de datos PostgreSQL. Implementa operaciones CRUD y consultas avanzadas usando procedimientos almacenados.

## Tecnologías

- **Java**: 21
- **Spring Boot**: 3.3.3
- **WebFlux**: Programación reactiva
- **R2DBC**: Base de datos reactiva
- **PostgreSQL**: Base de datos
- **Kotlin DSL**: Gradle

## Base de Datos

### Tabla: productos
```sql
CREATE TABLE productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    activo BOOLEAN NOT NULL DEFAULT true,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Procedimientos Almacenados
- `productos_bajo_stock(minimo INTEGER)`: Productos con stock bajo
- `actualizar_stock(producto_id BIGINT, cantidad INTEGER)`: Actualizar stock

## Configuración

### Variables de Entorno
- `DB_URL`: URL PostgreSQL (r2dbc:postgresql://...)
- `DB_USERNAME`: Usuario BD
- `DB_PASSWORD`: Contraseña BD

### Perfiles
- **dev**: Desarrollo (puerto 8081)
- **qa**: QA (puerto 8081)
- **prd**: Producción (puerto 8081)

## Ejecución

```bash
./gradlew bootRun --args="--spring.profiles.active=dev"
```

## API Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Listar productos |
| GET | `/api/productos/{id}` | Obtener producto |
| POST | `/api/productos` | Crear producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |
| PUT | `/api/productos/{id}/stock` | Actualizar stock |
| GET | `/api/productos/bajo-stock` | Productos con stock bajo |

## Ejemplos

### Crear producto
```bash
POST /api/productos
{
  "nombre": "Laptop Dell",
  "descripcion": "Laptop i7 16GB",
  "precio": 1200.00,
  "stock": 10
}
```

### Actualizar stock
```bash
PUT /api/productos/1/stock?cantidad=5
```

### Productos bajo stock
```bash
GET /api/productos/bajo-stock?minimo=20
```
