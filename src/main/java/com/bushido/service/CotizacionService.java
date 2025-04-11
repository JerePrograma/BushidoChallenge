package com.bushido.service;

import com.bushido.dto.request.ExchangeRateResponse;
import com.bushido.entity.Cotizacion;
import com.bushido.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CotizacionService {

    private final CotizacionRepository cotizacionRepository;
    private final RestTemplate restTemplate;

    @Value("${exchangerate.access_key}")
    private String accessKey;

    public CotizacionService(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.restTemplate = new RestTemplate();
    }

    public Cotizacion consultarYGuardarCotizacion(String monedaBase, String monedaDestino) {
        String url = "https://api.exchangerate.host/live?access_key=" + accessKey +
                "&source=" + monedaBase + "&currencies=" + monedaDestino;
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
        if (response == null || !response.success()) {
            throw new RuntimeException("Error en la API externa: " + response);
        }
        Float rate = response.quotes().get(monedaBase + monedaDestino);
        if (rate == null) {
            throw new RuntimeException("No se encontró la tasa para " + monedaBase + monedaDestino);
        }
        LocalDateTime fechaConsulta = LocalDateTime.ofInstant(Instant.ofEpochSecond(response.timestamp()), ZoneId.systemDefault());
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setFechaConsulta(fechaConsulta);
        cotizacion.setMonedaBase(monedaBase);
        cotizacion.setMonedaDestino(monedaDestino);
        cotizacion.setValorCotizacion(rate.doubleValue());
        return cotizacionRepository.save(cotizacion);
    }
}
