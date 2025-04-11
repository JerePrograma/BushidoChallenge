package com.bushido.repository;

import com.bushido.entity.Perfil;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, UUID> {
    List<Perfil> findByActivoTrue();
}
