package com.codefactory.reservasmsreservationservice.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ChangeReservationStatusRequestDTO Tests")
class ChangeReservationStatusRequestDTOTest {

    @Test
    @DisplayName("DTO creation")
    void dto_Created() {
        ChangeReservationStatusRequestDTO dto = new ChangeReservationStatusRequestDTO();
        dto.setEstado("COMPLETADA");
        assertThat(dto).isNotNull();
    }
}