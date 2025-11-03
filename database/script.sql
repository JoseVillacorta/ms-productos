-- =========================================
-- SCRIPTS PARA MS-PRODUCTOS
-- =========================================

-- Crear tabla productos
CREATE TABLE IF NOT EXISTS productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT true,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Procedimiento para actualizar stock (EXACTAMENTE como pidió el profesor)
CREATE OR REPLACE FUNCTION actualizar_stock(
    p_producto_id BIGINT,
    p_cantidad INTEGER
) RETURNS VOID AS $$
BEGIN
    UPDATE productos
    SET stock = stock - p_cantidad
    WHERE id = p_producto_id;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento para obtener productos con bajo stock (EXACTAMENTE como pidió el profesor)
CREATE OR REPLACE FUNCTION productos_bajo_stock(
    p_minimo INTEGER
) RETURNS TABLE(
    id BIGINT,
    nombre VARCHAR,
    stock INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT p.id, p.nombre, p.stock
    FROM productos p
    WHERE p.stock < p_minimo AND p.activo = true;
END;
$$ LANGUAGE plpgsql;

-- Insertar datos de prueba
INSERT INTO productos (nombre, descripcion, precio, stock, activo, fecha_creacion) VALUES
('Laptop Dell', 'Laptop i7 16GB RAM', 1200.00, 10, true, CURRENT_TIMESTAMP),
('Mouse Logitech', 'Mouse inalámbrico', 25.00, 50, true, CURRENT_TIMESTAMP),
('Teclado Mecánico', 'Teclado RGB', 80.00, 30, true, CURRENT_TIMESTAMP),
('Monitor Samsung', 'Monitor 27" 4K', 350.00, 15, true, CURRENT_TIMESTAMP),
('Disco Duro SSD', 'SSD 1TB NVMe', 120.00, 25, true, CURRENT_TIMESTAMP);

-- Verificar datos
SELECT COUNT(*) as total_productos FROM productos WHERE activo = true;
