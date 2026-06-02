package com.codefactory.reservasmsreservationservice.config;

import feign.RequestInterceptor;
import feign.httpclient.ApacheHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FeignConfig Tests")
class FeignConfigTest {

    private FeignConfig feignConfig;

    @BeforeEach
    void setUp() {
        feignConfig = new FeignConfig();
    }

    @Test
    @DisplayName("FeignConfig implements RequestInterceptor")
    void implementsRequestInterceptor() {
        assertThat(feignConfig).isInstanceOf(RequestInterceptor.class);
    }

    @Test
    @DisplayName("httpClient bean creation")
    void httpClient_BeanCreated() {
        ApacheHttpClient client = feignConfig.httpClient();
        assertThat(client).isNotNull();
    }

    @Test
    @DisplayName("FeignConfig instance creation")
    void instanceCreation() {
        FeignConfig config = new FeignConfig();
        assertThat(config).isNotNull();
    }
}