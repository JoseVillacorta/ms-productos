package org.example.repository;

import org.example.entity.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends R2dbcRepository<Producto, Long> {

    @Query("SELECT * FROM productos WHERE activo = true")
    Flux<Producto> findByActivoTrue();

    @Query("CALL actualizar_stock(:productoId, :cantidad)")
    Mono<Void> actualizarStock(@Param("productoId") Long productoId, @Param("cantidad") Integer cantidad);

    @Query("SELECT * FROM productos_bajo_stock(:minimo)")
    Flux<Object[]> obtenerProductosBajoStock(@Param("minimo") Integer minimo);
}
