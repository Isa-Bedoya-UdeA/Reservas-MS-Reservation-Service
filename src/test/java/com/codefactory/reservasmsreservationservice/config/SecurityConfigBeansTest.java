package com.codefactory.reservasmsreservationservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SecurityConfigBeans Tests")
class SecurityConfigBeansTest {

    @Test
    @DisplayName("Config beans creation")
    void config_Created() {
        SecurityConfigBeans beans = new SecurityConfigBeans();
        assertThat(beans).isNotNull();
    }
}