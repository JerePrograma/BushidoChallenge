package com.bushido.controller;

import com.bushido.dto.response.CotizacionResponse;
import com.bushido.entity.Cotizacion;
import com.bushido.service.CotizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/cotizacion")
@Tag(name = "Cotizaciones", description = "Endpoints para consultar y guardar cotizaciones")
public class CotizacionController {

    private final CotizacionService cotizacionService;

    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    @Operation(summary = "Consultar y guardar cotización", description = "Obtiene la cotización de la moneda destino utilizando la moneda base y la guarda en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvo y guardó la cotización correctamente"),
            @ApiResponse(responseCode = "500", description = "Error en la consulta o en el servicio externo")
    })
    @GetMapping
    public ResponseEntity<CotizacionResponse> obtenerCotizacion(
            @Parameter(description = "Moneda base (ej: USD)", required = true, example = "USD") @RequestParam @NotBlank String monedaBase,
            @Parameter(description = "Moneda destino (ej: ARS)", required = true, example = "ARS") @RequestParam @NotBlank String monedaDestino) {

        Cotizacion cotizacion = cotizacionService.consultarYGuardarCotizacion(monedaBase, monedaDestino);
        CotizacionResponse response = new CotizacionResponse(cotizacion.getId(),
                cotizacion.getFechaConsulta(), cotizacion.getMonedaBase(), cotizacion.getMonedaDestino(), cotizacion.getValorCotizacion());
        return ResponseEntity.ok(response);
    }
}
