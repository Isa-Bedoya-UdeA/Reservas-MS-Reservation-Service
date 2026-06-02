package com.codefactory.reservasmsreservationservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ExternalServiceException Tests")
class ExternalServiceExceptionTest {

    @Test
    @DisplayName("Exception creation")
    void exception_Created() {
        ExternalServiceException ex = ExternalServiceException.unavailable("TestService");
        assertThat(ex).isNotNull();
        assertThat(ex.getServiceName()).isEqualTo("TestService");
        assertThat(ex.getStatusCode()).isEqualTo(503);
    }
}