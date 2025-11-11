# Microservicio de Productos

Gestión de catálogo de productos con operaciones CRUD completas.

## Funcionalidades

- **CRUD productos**: Crear, leer, actualizar, eliminar productos
- **Control de stock**: Incremento/decremento automático
- **Base de datos reactiva**: R2DBC con PostgreSQL
- **API REST**: Endpoints funcionales con WebFlux
- **Validación de stock**: Prevención de ventas sin inventario
- **OAuth2 Resource Server**: Validación de tokens JWT
- **Endpoints protegidos**: Todos requieren autenticación Bearer Token

## Endpoints Protegidos (requieren Bearer Token JWT)

- `GET /products` → Listar todos los productos
- `GET /products/{id}` → Obtener producto específico
- `POST /products` → Crear nuevo producto
- `PUT /products/{id}` → Actualizar producto
- `PUT /products/{id}/stock` → Actualizar stock
- `GET /products/bajo-stock` → Productos con stock bajo
- `DELETE /products/{id}` → Eliminar producto
- `GET /resources/user` → Información del usuario autenticado

## Configuración Docker

- **Puerto**: 8081
- **Base de datos**: `db:5432` (PostgreSQL en contenedor)
- **Perfil**: docker
- **Eureka**: `registry-service:8761`
- **OAuth2**: Resource Server con JWT validation
- **Issuer**: `http://oauth-server:9000`


```

## Despliegue

```bash
docker-compose up --build ms-productos
```

## Health Check

- Endpoint: `http://localhost:8081/actuator/health`
- Estado esperado: `{"status":"UP"}`

## Testing OAuth2

### Obtener Token (Password Grant)
```bash
POST http://localhost:9000/oauth2/token
Authorization: Basic (oauth-client:12345678910)
Body: grant_type=password&username=jose&password=123456&scope=read
```

### Usar Token en API
```bash
GET http://localhost:8081/resources/user
Authorization: Bearer [access_token]
```

## Notas

- Requiere PostgreSQL corriendo en puerto 5432 local
- Todos los endpoints requieren autenticación JWT
- Validación automática de tokens JWT desde oauth-server
