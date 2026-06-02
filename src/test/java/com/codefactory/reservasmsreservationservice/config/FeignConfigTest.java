package com.codefactory.reservasmsreservationservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FeignConfig Tests")
class FeignConfigTest {

    @Test
    @DisplayName("ApacheHttpClient bean creation")
    void httpClient_Created() {
        FeignConfig config = new FeignConfig();
        var client = config.httpClient();
        assertThat(client).isNotNull();
    }

    @Test
    @DisplayName("Config instance creation")
    void config_Created() {
        FeignConfig config = new FeignConfig();
        assertThat(config).isNotNull();
    }
}