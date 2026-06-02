package com.codefactory.reservasmsreservationservice.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CancelReservationRequestDTO Tests")
class CancelReservationRequestDTOTest {

    @Test
    @DisplayName("DTO creation")
    void dto_Created() {
        CancelReservationRequestDTO dto = new CancelReservationRequestDTO();
        dto.setComentariosCancelacion("Cliente canceló");
        assertThat(dto).isNotNull();
    }
}