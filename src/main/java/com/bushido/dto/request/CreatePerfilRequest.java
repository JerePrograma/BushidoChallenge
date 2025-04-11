package com.bushido.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePerfilRequest(
        @NotBlank(message = "El nombre del perfil es obligatorio") String nombre
) {
}
