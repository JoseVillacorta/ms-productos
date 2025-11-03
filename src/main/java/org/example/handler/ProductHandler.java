package org.example.handler;

import org.example.entity.Producto;
import org.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productoService;

    public Mono<ServerResponse> obtenerTodos(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productoService.obtenerTodos(), Producto.class);
    }

    public Mono<ServerResponse> obtenerPorId(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productoService.obtenerPorId(id)
                .flatMap(producto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(producto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> crear(ServerRequest request) {
        return request.bodyToMono(Producto.class)
                .flatMap(productoService::crear)
                .flatMap(producto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(producto));
    }

    public Mono<ServerResponse> actualizar(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Producto.class)
                .flatMap(producto -> productoService.actualizar(id, producto))
                .flatMap(producto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(producto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> eliminar(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productoService.eliminar(id)
                .flatMap(eliminado -> eliminado ?
                        ServerResponse.noContent().build() :
                        ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> actualizarStock(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Integer cantidad = Integer.valueOf(request.queryParam("cantidad").orElse("0"));
        return productoService.actualizarStock(id, cantidad)
                .then(ServerResponse.ok().build())
                .onErrorResume(e -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> obtenerProductosBajoStock(ServerRequest request) {
        Integer minimo = Integer.valueOf(request.queryParam("minimo").orElse("10"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productoService.obtenerProductosBajoStock(minimo), Object[].class);
    }
}
