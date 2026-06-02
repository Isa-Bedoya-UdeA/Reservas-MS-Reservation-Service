package com.codefactory.reservasmsreservationservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReservationAccessDeniedException Tests")
class ReservationAccessDeniedExceptionTest {

    @Test
    @DisplayName("Exception creation")
    void exception_Created() {
        ReservationAccessDeniedException ex = new ReservationAccessDeniedException("Acceso denegado");
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).contains("Acceso denegado");
    }
}