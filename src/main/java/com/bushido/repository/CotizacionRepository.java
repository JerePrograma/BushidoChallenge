package com.bushido.repository;

import com.bushido.entity.Cotizacion;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionRepository extends JpaRepository<Cotizacion, UUID> {
}
