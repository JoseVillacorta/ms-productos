package org.example.service;

import org.example.entity.Producto;
import org.example.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productoRepository;

    public Flux<Producto> obtenerTodos() {
        return productoRepository.findByActivoTrue();
    }

    public Mono<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .filter(producto -> producto.getActivo() != null && producto.getActivo());
    }

    public Mono<Producto> crear(Producto producto) {
        producto.setActivo(true);
        producto.setFechaCreacion(java.time.LocalDateTime.now());
        return productoRepository.save(producto);
    }

    public Mono<Producto> actualizar(Long id, Producto productoActualizado) {
        return productoRepository.findById(id)
                .filter(producto -> producto.getActivo() != null && producto.getActivo())
                .flatMap(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setDescripcion(productoActualizado.getDescripcion());
                    producto.setPrecio(productoActualizado.getPrecio());
                    producto.setStock(productoActualizado.getStock());
                    return productoRepository.save(producto);
                });
    }

    public Mono<Boolean> eliminar(Long id) {
        return productoRepository.findById(id)
                .filter(producto -> producto.getActivo() != null && producto.getActivo())
                .flatMap(producto -> {
                    producto.setActivo(false);
                    return productoRepository.save(producto).then(Mono.just(true));
                })
                .defaultIfEmpty(false);
    }

    public Mono<Void> actualizarStock(Long productoId, Integer cantidad) {
        return productoRepository.actualizarStock(productoId, cantidad);
    }

    public Flux<Object[]> obtenerProductosBajoStock(Integer minimo) {
        return productoRepository.obtenerProductosBajoStock(minimo);
    }
}
