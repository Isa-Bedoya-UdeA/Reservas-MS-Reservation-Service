package com.codefactory.reservasmsreservationservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ValidationException Tests")
class ValidationExceptionTest {

    @Test
    @DisplayName("Exception creation")
    void exception_Created() {
        ValidationException ex = new ValidationException("Validation failed");
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).contains("Validation failed");
    }
}