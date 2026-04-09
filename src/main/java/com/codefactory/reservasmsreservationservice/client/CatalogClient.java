package com.codefactory.reservasmsreservationservice.client;

import com.codefactory.reservasmsreservationservice.config.FeignConfig;
import com.codefactory.reservasmsreservationservice.dto.external.ExternalServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "catalog-service",
        url = "${services.catalog-service.url}",
        configuration = FeignConfig.class
)
public interface CatalogClient {

    /**
     * Obtiene los detalles de un servicio para validar existencia y obtener duración/precio
     */
    @GetMapping("/api/services/{id}")
    ResponseEntity<ExternalServiceDTO> getServiceById(@PathVariable("id") Long id);
}