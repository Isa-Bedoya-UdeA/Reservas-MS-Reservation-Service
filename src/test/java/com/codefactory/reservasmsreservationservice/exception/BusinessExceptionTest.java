package com.codefactory.reservasmsreservationservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BusinessException Tests")
class BusinessExceptionTest {

    @Test
    @DisplayName("Exception creation")
    void exception_Created() {
        BusinessException ex = new BusinessException("Business error");
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).contains("Business error");
    }
}