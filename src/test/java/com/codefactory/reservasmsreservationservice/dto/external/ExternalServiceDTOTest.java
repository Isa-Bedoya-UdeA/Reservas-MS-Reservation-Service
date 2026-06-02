package com.codefactory.reservasmsreservationservice.dto.external;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ExternalServiceDTO Tests")
class ExternalServiceDTOTest {

    @Test
    @DisplayName("DTO creation")
    void dto_Created() {
        ExternalServiceDTO dto = new ExternalServiceDTO();
        dto.setId(UUID.randomUUID());
        dto.setNombreServicio("Corte de cabello");
        dto.setActivo(true);
        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("isActivo works correctly")
    void isActivo_ReturnsCorrectly() {
        ExternalServiceDTO dto = new ExternalServiceDTO();
        dto.setActivo(false);
        assertThat(dto.isActivo()).isFalse();
    }
}