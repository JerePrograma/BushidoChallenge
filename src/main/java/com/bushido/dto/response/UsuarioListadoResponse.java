package com.bushido.dto.response;

import java.util.Set;
import java.util.UUID;

public record UsuarioListadoResponse(
        UUID id,
        String nombre,
        String email,
        Set<PerfilResponse> perfiles
) {
}
