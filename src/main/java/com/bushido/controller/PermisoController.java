package com.bushido.controller;

import com.bushido.dto.request.CreatePermisoRequest;
import com.bushido.dto.request.UpdatePermisoRequest;
import com.bushido.dto.response.PermisoResponse;
import com.bushido.dto.response.PerfilResponse;
import com.bushido.entity.Permiso;
import com.bushido.service.PermisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permisos")
@Tag(name = "Permisos", description = "Endpoints para operaciones CRUD sobre permisos y modificación de accesos")
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    @Operation(summary = "Listar permisos activos", description = "Devuelve la lista de permisos activos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida con éxito")
    })
    @GetMapping
    public ResponseEntity<List<PermisoResponse>> listarPermisos() {
        List<Permiso> permisos = permisoService.listarPermisosActivos();
        List<PermisoResponse> response = permisos.stream().map(p -> {
            PerfilResponse perfilResp = new PerfilResponse(p.getPerfil().getId(), p.getPerfil().getNombre());
            return new PermisoResponse(p.getId(), p.getNombre(), p.getAcceso().name(), perfilResp);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear permiso", description = "Crea un nuevo permiso asociado a un perfil")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permiso creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<PermisoResponse> crearPermiso(@RequestBody @Valid CreatePermisoRequest request) {
        Permiso permiso = new Permiso();
        permiso.setNombre(request.nombre());
        permiso.setAcceso(Enum.valueOf(com.bushido.entity.enums.Acceso.class, request.acceso()));
        Permiso nuevoPermiso = permisoService.crearPermiso(permiso, request.perfilId());
        PerfilResponse perfilResp = new PerfilResponse(nuevoPermiso.getPerfil().getId(), nuevoPermiso.getPerfil().getNombre());
        PermisoResponse response = new PermisoResponse(nuevoPermiso.getId(), nuevoPermiso.getNombre(), nuevoPermiso.getAcceso().name(), perfilResp);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar permiso", description = "Actualiza la información y acceso de un permiso existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permiso actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PermisoResponse> actualizarPermiso(@PathVariable UUID id, @RequestBody @Valid UpdatePermisoRequest request) {
        Permiso permiso = new Permiso();
        permiso.setNombre(request.nombre());
        permiso.setAcceso(Enum.valueOf(com.bushido.entity.enums.Acceso.class, request.acceso()));
        Permiso actualizado = permisoService.actualizarPermiso(id, permiso);
        PerfilResponse perfilResp = new PerfilResponse(actualizado.getPerfil().getId(), actualizado.getPerfil().getNombre());
        PermisoResponse response = new PermisoResponse(actualizado.getId(), actualizado.getNombre(), actualizado.getAcceso().name(), perfilResp);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar permiso", description = "Elimina lógicamente un permiso")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Permiso eliminado lógicamente"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPermiso(@PathVariable UUID id) {
        permisoService.eliminarPermiso(id);
        return ResponseEntity.noContent().build();
    }
}
