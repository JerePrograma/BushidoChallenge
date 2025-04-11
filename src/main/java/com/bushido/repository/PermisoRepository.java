package com.bushido.repository;

import com.bushido.entity.Permiso;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermisoRepository extends JpaRepository<Permiso, UUID> {
    List<Permiso> findByActivoTrue();
}
