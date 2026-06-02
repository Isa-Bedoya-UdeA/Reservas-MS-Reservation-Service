package com.codefactory.reservasmsreservationservice.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtAuthenticationEntryPoint Tests")
class JwtAuthenticationEntryPointTest {

    @Test
    @DisplayName("Entry point creation")
    void entryPoint_Created() {
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();
        assertThat(entryPoint).isNotNull();
    }
}