package com.codefactory.reservasmsreservationservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ResourceNotFoundException Tests")
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Exception creation with message")
    void exception_Created() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Test not found");
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).contains("Test not found");
    }
}