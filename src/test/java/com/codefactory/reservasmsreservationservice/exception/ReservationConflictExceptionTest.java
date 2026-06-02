package com.codefactory.reservasmsreservationservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReservationConflictException Tests")
class ReservationConflictExceptionTest {

    @Test
    @DisplayName("Exception creation")
    void exception_Created() {
        ReservationConflictException ex = new ReservationConflictException("Conflict");
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).contains("Conflict");
    }
}