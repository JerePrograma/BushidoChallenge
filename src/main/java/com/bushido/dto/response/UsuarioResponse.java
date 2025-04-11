package com.bushido.dto.response;

import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nombre,
        String email) {
}
