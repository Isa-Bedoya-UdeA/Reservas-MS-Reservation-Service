package com.codefactory.reservasmsreservationservice.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtAccessDeniedHandler Tests")
class JwtAccessDeniedHandlerTest {

    @Test
    @DisplayName("Handler creation")
    void handler_Created() {
        JwtAccessDeniedHandler handler = new JwtAccessDeniedHandler();
        assertThat(handler).isNotNull();
    }
}