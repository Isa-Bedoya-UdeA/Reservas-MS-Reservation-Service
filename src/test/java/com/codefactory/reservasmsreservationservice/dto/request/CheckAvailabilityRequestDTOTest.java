package com.codefactory.reservasmsreservationservice.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CheckAvailabilityRequestDTO Tests")
class CheckAvailabilityRequestDTOTest {

    @Test
    @DisplayName("DTO creation")
    void dto_Created() {
        CheckAvailabilityRequestDTO dto = new CheckAvailabilityRequestDTO();
        dto.setIdEmpleado(UUID.randomUUID());
        dto.setFechaHoraInicio("2025-08-20T10:00:00Z");
        dto.setFechaHoraFin("2025-08-20T11:00:00Z");
        assertThat(dto).isNotNull();
    }
}