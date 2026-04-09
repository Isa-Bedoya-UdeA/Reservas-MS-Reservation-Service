package com.codefactory.reservasmsreservationservice.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * DTO enviado a MS-RESOURCE-SERVICE para validar si un recurso está libre en un rango de tiempo.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckAvailabilityRequestDTO {
    @NotNull
    private Long resourceId;

    @NotNull
    @FutureOrPresent
    private Instant startTime;

    @NotNull
    @FutureOrPresent
    private Instant endTime;
}