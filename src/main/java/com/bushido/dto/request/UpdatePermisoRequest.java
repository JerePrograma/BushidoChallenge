package com.bushido.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePermisoRequest(
        @NotBlank(message = "El nombre del permiso es obligatorio") String nombre,
        @NotBlank(message = "El acceso del permiso es obligatorio") String acceso
) {
}
