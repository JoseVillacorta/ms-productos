package org.example.service;

import org.example.classes.Producto;
import org.example.repository.ProductRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final DatabaseClient databaseClient;

    public ProductService(ProductRepository repository, DatabaseClient databaseClient) {
        this.repository = repository;
        this.databaseClient = databaseClient;
    }

    public Flux<Producto> findAll() {
        return repository.findAll();
    }

    public Mono<Producto> findById(Long id) {
        return repository.findById(id);
    }

    public Mono<Producto> save(Producto producto) {
        return repository.save(producto);
    }

    public Mono<Producto> update(Long id, Producto updated) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(existing -> {
                    existing.setNombre(updated.getNombre());
                    existing.setDescripcion(updated.getDescripcion());
                    existing.setStock(updated.getStock());
                    existing.setPrecio(updated.getPrecio());
                    return repository.save(existing);
                });
    }

    public Mono<Void> actualizarStock(Long productoId, Integer cantidad) {
        return databaseClient.sql("SELECT actualizar_stock($1, $2)")
                .bind(0, productoId)
                .bind(1, cantidad)
                .then();
    }

    public Flux<Map<String, Object>> obtenerProductosBajoStock(Integer minimo) {
        return databaseClient.sql("SELECT * FROM productos_bajo_stock($1)")
                .bind(0, minimo)
                .fetch().all();
    }

    public Mono<Producto> increaseStock(Long id, int amount) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(producto -> {
                    int newStock = producto.getStock() + amount;
                    producto.setStock(newStock);
                    return repository.save(producto);
                });
    }

    public Mono<Producto> decreaseStock(Long id, int amount) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(producto -> {
                    int newStock = producto.getStock() - amount;
                    if (newStock < 0) {
                        return Mono.error(new IllegalArgumentException("No hay suficiente stock para la operación"));
                    }
                    producto.setStock(newStock);
                    return repository.save(producto);
                });
    }

    public Mono<Producto> updateStock(Long id, Integer newStock) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(producto -> {
                    producto.setStock(newStock);
                    return repository.save(producto);
                });
    }

    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
