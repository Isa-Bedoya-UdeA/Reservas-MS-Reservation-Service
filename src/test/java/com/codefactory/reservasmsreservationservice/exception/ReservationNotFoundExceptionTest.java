package com.codefactory.reservasmsreservationservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReservationNotFoundException Tests")
class ReservationNotFoundExceptionTest {

    @Test
    @DisplayName("Exception creation")
    void exception_Created() {
        ReservationNotFoundException ex = new ReservationNotFoundException("Reserva no encontrada");
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).contains("Reserva no encontrada");
    }
}