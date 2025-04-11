package com.bushido.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePermisoRequest(
        @NotBlank(message = "El nombre del permiso es obligatorio") String nombre,
        @NotBlank(message = "El acceso del permiso es obligatorio") String acceso,
        @NotNull(message = "El id del perfil es obligatorio") UUID perfilId
) {
}
