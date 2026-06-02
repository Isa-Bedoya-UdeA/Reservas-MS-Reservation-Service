package com.codefactory.reservasmsreservationservice.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CreateReservationRequestDTO Tests")
class CreateReservationRequestDTOTest {

    @Test
    @DisplayName("DTO creation")
    void dto_Created() {
        CreateReservationRequestDTO dto = new CreateReservationRequestDTO();
        dto.setIdServicio(UUID.randomUUID());
        dto.setIdEmpleado(UUID.randomUUID());
        dto.setFechaHoraInicio("2025-08-20T10:00:00Z");
        assertThat(dto).isNotNull();
    }
}