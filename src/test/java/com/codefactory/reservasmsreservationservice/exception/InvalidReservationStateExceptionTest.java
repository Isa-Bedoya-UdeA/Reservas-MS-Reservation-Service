package com.codefactory.reservasmsreservationservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InvalidReservationStateException Tests")
class InvalidReservationStateExceptionTest {

    @Test
    @DisplayName("Exception creation")
    void exception_Created() {
        InvalidReservationStateException ex = new InvalidReservationStateException("Invalid state");
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).contains("Invalid state");
    }
}