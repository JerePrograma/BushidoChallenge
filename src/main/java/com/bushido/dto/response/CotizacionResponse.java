package com.bushido.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CotizacionResponse(UUID id, LocalDateTime fechaConsulta, String monedaBase, String monedaDestino, Double valorCotizacion) { }
