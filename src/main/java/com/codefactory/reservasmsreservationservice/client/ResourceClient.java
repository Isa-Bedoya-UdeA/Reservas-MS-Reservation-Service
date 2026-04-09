package com.codefactory.reservasmsreservationservice.client;

import com.codefactory.reservasmsreservationservice.config.FeignConfig;
import com.codefactory.reservasmsreservationservice.dto.external.ExternalResourceDTO;
import com.codefactory.reservasmsreservationservice.dto.request.CheckAvailabilityRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "resource-service",
        url = "${services.resource-service.url}",
        configuration = FeignConfig.class
)
public interface ResourceClient {

    /**
     * Obtiene los detalles de un recurso (ej. Consultorio 1, Profesional X)
     */
    @GetMapping("/api/resources/{id}")
    ResponseEntity<ExternalResourceDTO> getResourceById(@PathVariable("id") Long id);

    /**
     * Valida si un recurso está disponible en un rango de tiempo específico
     * (Lógica crítica para evitar solapamiento de reservas)
     */
    @PostMapping("/api/availability/check")
    ResponseEntity<Boolean> checkAvailability(@RequestBody CheckAvailabilityRequestDTO request);
}