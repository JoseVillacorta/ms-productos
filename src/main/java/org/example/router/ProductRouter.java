package org.example.router;

import org.example.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> productoRoutes(ProductHandler handler) {
        return RouterFunctions.route()
                .GET("/api/productos/bajo-stock", handler::obtenerProductosBajoStock)
                .GET("/api/productos", handler::obtenerTodos)
                .GET("/api/productos/{id}", handler::obtenerPorId)
                .POST("/api/productos", handler::crear)
                .PUT("/api/productos/{id}", handler::actualizar)
                .DELETE("/api/productos/{id}", handler::eliminar)
                .PUT("/api/productos/{id}/stock", handler::actualizarStock)
                .build();
    }
}
