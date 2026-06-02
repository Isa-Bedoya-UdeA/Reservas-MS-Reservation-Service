package com.codefactory.reservasmsreservationservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("OpenApiConfig Tests")
class OpenApiConfigTest {

    private OpenApiConfig openApiConfig;

    @BeforeEach
    void setUp() {
        openApiConfig = new OpenApiConfig();
        ReflectionTestUtils.setField(openApiConfig, "serverUrl", "");
    }

    @Test
    @DisplayName("customOpenAPI creates OpenAPI bean")
    void customOpenAPI() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getInfo()).isNotNull();
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Reservas MS - Reservation Service API");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("1.0.0");
    }

    @Test
    @DisplayName("OpenAPI has contact information")
    void openAPIHasContact() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        assertThat(openAPI.getInfo().getContact()).isNotNull();
        assertThat(openAPI.getInfo().getContact().getName()).isEqualTo("CodeFactory Team");
        assertThat(openAPI.getInfo().getContact().getEmail()).isEqualTo("plataformareservas.codefactory@gmail.com");
    }

    @Test
    @DisplayName("OpenAPI has license information")
    void openAPIHasLicense() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        assertThat(openAPI.getInfo().getLicense()).isNotNull();
        assertThat(openAPI.getInfo().getLicense().getName()).isEqualTo("Apache 2.0");
    }

    @Test
    @DisplayName("OpenAPI has servers configured")
    void openAPIHasServers() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        assertThat(openAPI.getServers()).isNotEmpty();
        assertThat(openAPI.getServers().get(0).getUrl()).contains("localhost:8084");
    }

    @Test
    @DisplayName("OpenAPI has security scheme configured")
    void openAPIHasSecurityScheme() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        assertThat(openAPI.getComponents().getSecuritySchemes()).isNotNull();
        assertThat(openAPI.getComponents().getSecuritySchemes().get("bearerAuth")).isNotNull();
    }

    @Test
    @DisplayName("OpenApiConfig instance creation")
    void instanceCreation() {
        OpenApiConfig config = new OpenApiConfig();
        assertThat(config).isNotNull();
    }

    @Test
    @DisplayName("OpenAPI has security requirement")
    void openAPIHasSecurityRequirement() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        assertThat(openAPI.getSecurity()).isNotEmpty();
    }

    @Test
    @DisplayName("OpenAPI has description")
    void openAPIHasDescription() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        assertThat(openAPI.getInfo().getDescription()).isNotEmpty();
        assertThat(openAPI.getInfo().getDescription()).contains("gestión de reservas");
    }

    @Test
    @DisplayName("OpenAPI description mentions clients")
    void openAPIDescriptionMentionsClients() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        String description = openAPI.getInfo().getDescription();
        assertThat(description).contains("clientes");
    }
}