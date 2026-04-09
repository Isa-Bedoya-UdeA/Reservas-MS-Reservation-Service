package com.codefactory.reservasmsreservationservice.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO local que representa la respuesta de MS-CATALOG-SERVICE al consultar un servicio.
 * Se usa como Anti-Corruption Layer para evitar acoplamiento directo con el modelo del catálogo.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalServiceDTO {
    private Long id;
    private String nombreServicio;
    private Integer duracionMinutos;
    private BigDecimal precio;
    private String descripcion;
    private Boolean activo;
}