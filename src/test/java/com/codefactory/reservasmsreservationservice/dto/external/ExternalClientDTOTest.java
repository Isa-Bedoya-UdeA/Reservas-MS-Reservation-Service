package com.codefactory.reservasmsreservationservice.dto.external;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ExternalClientDTO Tests")
class ExternalClientDTOTest {

    @Test
    @DisplayName("DTO creation")
    void dto_Created() {
        ExternalClientDTO dto = new ExternalClientDTO();
        dto.setId(java.util.UUID.randomUUID());
        dto.setNombre("John Doe");
        dto.setEmail("john@example.com");
        dto.setActivo(true);
        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("isActivo works correctly")
    void isActivo_ReturnsCorrectly() {
        ExternalClientDTO dto = new ExternalClientDTO();
        dto.setActivo(true);
        assertThat(dto.isActivo()).isTrue();

        dto.setActivo(false);
        assertThat(dto.isActivo()).isFalse();
    }
}