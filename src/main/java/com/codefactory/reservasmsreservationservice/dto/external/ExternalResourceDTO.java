package com.codefactory.reservasmsreservationservice.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO local que representa la respuesta de MS-RESOURCE-SERVICE al consultar un recurso.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalResourceDTO {
    private Long id;
    private String nombreRecurso;
    private String tipoRecurso; // Ej: "SALA", "PROFESIONAL", "EQUIPO"
    private Integer capacidad;
    private Long idProveedor;
}