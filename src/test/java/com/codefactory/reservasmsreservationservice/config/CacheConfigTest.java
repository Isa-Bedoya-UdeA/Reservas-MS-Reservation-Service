package com.codefactory.reservasmsreservationservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CacheConfig Tests")
class CacheConfigTest {

    @Test
    @DisplayName("CacheManager bean creation")
    void cacheManagerBean_Created() {
        CacheConfig config = new CacheConfig();
        CacheManager cacheManager = config.cacheManager();
        assertThat(cacheManager).isNotNull();
        assertThat(cacheManager.getCache("clientCache")).isNotNull();
        assertThat(cacheManager.getCache("serviceCache")).isNotNull();
        assertThat(cacheManager.getCache("providerCache")).isNotNull();
        assertThat(cacheManager.getCache("employeeCache")).isNotNull();
    }
}