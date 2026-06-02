package com.codefactory.reservasmsreservationservice.security.impl;

import com.codefactory.reservasmsreservationservice.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtServiceImpl Tests")
class JwtServiceImplTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl();
        setSecretKey(jwtService, "test-secret-key-that-is-at-least-32-characters-long-for-hs256");
    }

    private void setSecretKey(JwtService service, String secret) {
        try {
            var field = service.getClass().getDeclaredField("secretKey");
            field.setAccessible(true);
            field.set(service, secret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Service instantiation")
    void service_Created() {
        assertThat(jwtService).isNotNull();
    }
}