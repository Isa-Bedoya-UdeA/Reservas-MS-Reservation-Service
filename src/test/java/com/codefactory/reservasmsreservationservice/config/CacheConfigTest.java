package com.codefactory.reservasmsreservationservice.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CacheConfig Tests")
class CacheConfigTest {

    private CacheConfig cacheConfig;
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheConfig = new CacheConfig();
        cacheManager = cacheConfig.cacheManager();
    }

    @Test
    @DisplayName("CLIENT_CACHE constant exists")
    void clientCacheConstant() {
        assertThat(CacheConfig.CLIENT_CACHE).isEqualTo("clientCache");
    }

    @Test
    @DisplayName("SERVICE_CACHE constant exists")
    void serviceCacheConstant() {
        assertThat(CacheConfig.SERVICE_CACHE).isEqualTo("serviceCache");
    }

    @Test
    @DisplayName("PROVIDER_CACHE constant exists")
    void providerCacheConstant() {
        assertThat(CacheConfig.PROVIDER_CACHE).isEqualTo("providerCache");
    }

    @Test
    @DisplayName("EMPLOYEE_CACHE constant exists")
    void employeeCacheConstant() {
        assertThat(CacheConfig.EMPLOYEE_CACHE).isEqualTo("employeeCache");
    }

    @Test
    @DisplayName("cacheManager bean is created")
    void cacheManagerBean() {
        assertThat(cacheManager).isNotNull();
    }

    @Test
    @DisplayName("CacheManager has all expected cache names")
    void cacheManagerHasAllCaches() {
        assertThat(cacheManager.getCacheNames()).contains(
                CacheConfig.CLIENT_CACHE,
                CacheConfig.SERVICE_CACHE,
                CacheConfig.PROVIDER_CACHE,
                CacheConfig.EMPLOYEE_CACHE
        );
    }

    @Test
    @DisplayName("Client cache can be retrieved")
    void getClientCache() {
        var cache = cacheManager.getCache(CacheConfig.CLIENT_CACHE);
        assertThat(cache).isNotNull();
    }

    @Test
    @DisplayName("Service cache can be retrieved")
    void getServiceCache() {
        var cache = cacheManager.getCache(CacheConfig.SERVICE_CACHE);
        assertThat(cache).isNotNull();
    }

    @Test
    @DisplayName("Provider cache can be retrieved")
    void getProviderCache() {
        var cache = cacheManager.getCache(CacheConfig.PROVIDER_CACHE);
        assertThat(cache).isNotNull();
    }

    @Test
    @DisplayName("Employee cache can be retrieved")
    void getEmployeeCache() {
        var cache = cacheManager.getCache(CacheConfig.EMPLOYEE_CACHE);
        assertThat(cache).isNotNull();
    }
}