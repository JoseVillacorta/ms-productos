
package org.example.exception;

import java.time.Instant;

public record ApiError(Instant timestamp, int estado, String mensaje, String ruta) {}
