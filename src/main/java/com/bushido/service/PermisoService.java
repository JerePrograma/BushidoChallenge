package com.bushido.service;

import com.bushido.entity.Permiso;
import com.bushido.entity.Perfil;
import com.bushido.repository.PermisoRepository;
import com.bushido.repository.PerfilRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PermisoService {

    private final PermisoRepository permisoRepository;
    private final PerfilRepository perfilRepository;

    public PermisoService(PermisoRepository permisoRepository, PerfilRepository perfilRepository) {
        this.permisoRepository = permisoRepository;
        this.perfilRepository = perfilRepository;
    }

    public Permiso crearPermiso(Permiso permiso, UUID perfilId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado: " + perfilId));
        permiso.setPerfil(perfil);
        return permisoRepository.save(permiso);
    }

    public Permiso actualizarPermiso(UUID id, Permiso permisoActualizado) {
        Permiso permisoExistente = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        permisoExistente.setNombre(permisoActualizado.getNombre());
        permisoExistente.setAcceso(permisoActualizado.getAcceso());
        return permisoRepository.save(permisoExistente);
    }

    public void eliminarPermiso(UUID id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        permiso.setActivo(false);
        permisoRepository.save(permiso);
    }

    public List<Permiso> listarPermisosActivos() {
        return permisoRepository.findByActivoTrue();
    }
}
