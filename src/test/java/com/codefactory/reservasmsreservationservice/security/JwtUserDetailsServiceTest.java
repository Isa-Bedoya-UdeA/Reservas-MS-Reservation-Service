package com.codefactory.reservasmsreservationservice.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtUserDetailsService Tests")
class JwtUserDetailsServiceTest {

    @Test
    @DisplayName("Service creation")
    void service_Created() {
        JwtUserDetailsService service = new JwtUserDetailsService();
        assertThat(service).isNotNull();
    }
}