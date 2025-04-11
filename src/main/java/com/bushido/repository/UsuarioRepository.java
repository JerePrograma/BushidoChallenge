package com.bushido.repository;

import com.bushido.entity.Usuario;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    List<Usuario> findByActivoTrue();

    boolean existsByEmail(String email);
}
