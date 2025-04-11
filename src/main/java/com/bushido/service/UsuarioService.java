package com.bushido.service;

import com.bushido.entity.Usuario;
import com.bushido.entity.Perfil;
import com.bushido.repository.UsuarioRepository;
import com.bushido.repository.PerfilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está en uso.");
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizarUsuario(UUID usuarioId, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!usuarioExistente.getEmail().equals(usuarioActualizado.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioActualizado.getEmail())) {
            throw new RuntimeException("El email ya está en uso por otro usuario.");
        }
        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void eliminarUsuario(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!usuario.getActivo()) {
            throw new RuntimeException("El usuario ya está inactivo.");
        }
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    @Transactional
    public Usuario asignarPerfiles(UUID usuarioId, Set<UUID> perfilIds) {
        if (perfilIds == null || perfilIds.isEmpty()) {
            throw new RuntimeException("No se proporcionaron perfiles para asignar.");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Set<Perfil> perfilesAsignados = new HashSet<>();
        perfilIds.forEach(perfilId -> {
            Perfil perfil = perfilRepository.findById(perfilId)
                    .orElseThrow(() -> new RuntimeException("Perfil no encontrado: " + perfilId));
            perfilesAsignados.add(perfil);
        });

        usuario.setPerfiles(perfilesAsignados);
        return usuarioRepository.save(usuario);
    }
}
