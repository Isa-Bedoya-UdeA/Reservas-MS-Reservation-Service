package com.codefactory.reservasmsreservationservice.dto.external;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ExternalProviderDTO Tests")
class ExternalProviderDTOTest {

    @Test
    @DisplayName("DTO creation")
    void dto_Created() {
        ExternalProviderDTO dto = new ExternalProviderDTO();
        dto.setId(UUID.randomUUID());
        dto.setNombreComercial("Test Business");
        dto.setActivo(true);
        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("isActivo works correctly")
    void isActivo_ReturnsCorrectly() {
        ExternalProviderDTO dto = new ExternalProviderDTO();
        dto.setActivo(true);
        assertThat(dto.isActivo()).isTrue();
    }
}