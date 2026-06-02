package com.codefactory.reservasmsreservationservice.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReservationResponseDTO Tests")
class ReservationResponseDTOTest {

    @Test
    @DisplayName("DTO creation")
    void dto_Created() {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setIdReserva(UUID.randomUUID());
        dto.setClienteNombre("John Doe");
        assertThat(dto).isNotNull();
    }
}