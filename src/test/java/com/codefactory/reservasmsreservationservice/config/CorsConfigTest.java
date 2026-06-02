package com.codefactory.reservasmsreservationservice.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CorsConfig Tests")
class CorsConfigTest {

    private CorsConfig createCorsConfig(String frontendUrl) {
        CorsConfig config = new CorsConfig();
        ReflectionTestUtils.setField(config, "frontendUrl", frontendUrl);
        return config;
    }

    @Test
    @DisplayName("corsConfigurationSource creates UrlBasedCorsConfigurationSource")
    void corsConfigurationSource() {
        CorsConfig config = createCorsConfig("http://localhost:3000");

        CorsConfigurationSource source = config.corsConfigurationSource();

        assertThat(source).isNotNull();
        assertThat(source).isInstanceOf(UrlBasedCorsConfigurationSource.class);
    }

    @Test
    @DisplayName("CORS allows credentials")
    void corsAllowsCredentials() {
        CorsConfig config = createCorsConfig("http://localhost:3000");

        CorsConfigurationSource source = config.corsConfigurationSource();
        UrlBasedCorsConfigurationSource urlSource = (UrlBasedCorsConfigurationSource) source;
        CorsConfiguration corsConfig = urlSource.getCorsConfigurations().values().stream()
                .findFirst().orElse(null);

        assertThat(corsConfig).isNotNull();
        assertThat(corsConfig.getAllowCredentials()).isTrue();
    }

    @Test
    @DisplayName("CORS allows localhost origins")
    void corsAllowsLocalhostOrigins() {
        CorsConfig config = createCorsConfig("http://localhost:3000");

        CorsConfigurationSource source = config.corsConfigurationSource();
        UrlBasedCorsConfigurationSource urlSource = (UrlBasedCorsConfigurationSource) source;
        CorsConfiguration corsConfig = urlSource.getCorsConfigurations().values().stream()
                .findFirst().orElse(null);

        assertThat(corsConfig).isNotNull();
        assertThat(corsConfig.getAllowedOrigins()).contains(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://localhost:4200"
        );
    }

    @Test
    @DisplayName("CORS allows specified headers")
    void corsAllowsHeaders() {
        CorsConfig config = createCorsConfig("http://localhost:3000");

        CorsConfigurationSource source = config.corsConfigurationSource();
        UrlBasedCorsConfigurationSource urlSource = (UrlBasedCorsConfigurationSource) source;
        CorsConfiguration corsConfig = urlSource.getCorsConfigurations().values().stream()
                .findFirst().orElse(null);

        assertThat(corsConfig).isNotNull();
        assertThat(corsConfig.getAllowedHeaders()).contains(
                "Origin", "Content-Type", "Accept", "Authorization"
        );
    }

    @Test
    @DisplayName("CORS allows specified methods")
    void corsAllowsMethods() {
        CorsConfig config = createCorsConfig("http://localhost:3000");

        CorsConfigurationSource source = config.corsConfigurationSource();
        UrlBasedCorsConfigurationSource urlSource = (UrlBasedCorsConfigurationSource) source;
        CorsConfiguration corsConfig = urlSource.getCorsConfigurations().values().stream()
                .findFirst().orElse(null);

        assertThat(corsConfig).isNotNull();
        assertThat(corsConfig.getAllowedMethods()).contains(
                "GET", "POST", "PUT", "DELETE"
        );
    }

    @Test
    @DisplayName("CORS exposes Authorization header")
    void corsExposesAuthorizationHeader() {
        CorsConfig config = createCorsConfig("http://localhost:3000");

        CorsConfigurationSource source = config.corsConfigurationSource();
        UrlBasedCorsConfigurationSource urlSource = (UrlBasedCorsConfigurationSource) source;
        CorsConfiguration corsConfig = urlSource.getCorsConfigurations().values().stream()
                .findFirst().orElse(null);

        assertThat(corsConfig).isNotNull();
        assertThat(corsConfig.getExposedHeaders()).contains("Authorization");
    }

    @Test
    @DisplayName("CORS max age is 3600 seconds")
    void corsMaxAge() {
        CorsConfig config = createCorsConfig("http://localhost:3000");

        CorsConfigurationSource source = config.corsConfigurationSource();
        UrlBasedCorsConfigurationSource urlSource = (UrlBasedCorsConfigurationSource) source;
        CorsConfiguration corsConfig = urlSource.getCorsConfigurations().values().stream()
                .findFirst().orElse(null);

        assertThat(corsConfig).isNotNull();
        assertThat(corsConfig.getMaxAge()).isEqualTo(3600L);
    }
}