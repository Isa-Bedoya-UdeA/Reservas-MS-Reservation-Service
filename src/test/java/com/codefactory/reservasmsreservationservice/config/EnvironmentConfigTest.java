package com.codefactory.reservasmsreservationservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EnvironmentConfig Tests")
class EnvironmentConfigTest {

    @Test
    @DisplayName("Config bean creation")
    void config_Created() {
        EnvironmentConfig config = new EnvironmentConfig(null);
        assertThat(config).isNotNull();
    }
}