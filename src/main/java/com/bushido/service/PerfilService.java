package com.bushido.service;

import com.bushido.entity.Perfil;
import com.bushido.repository.PerfilRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public Perfil crearPerfil(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    public Perfil actualizarPerfil(UUID id, Perfil perfilActualizado) {
        Perfil perfilExistente = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        perfilExistente.setNombre(perfilActualizado.getNombre());
        return perfilRepository.save(perfilExistente);
    }

    public void eliminarPerfil(UUID id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        perfil.setActivo(false);
        perfilRepository.save(perfil);
    }

    public List<Perfil> listarPerfilesActivos() {
        return perfilRepository.findByActivoTrue();
    }
}
