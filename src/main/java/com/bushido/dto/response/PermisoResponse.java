package com.bushido.dto.response;

import java.util.UUID;

public record PermisoResponse(UUID id, String nombre, String acceso, PerfilResponse perfil) { }
