package com.codefactory.reservasmsreservationservice.dto.request;

import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UpdateReservationRequestDTO Tests")
class UpdateReservationRequestDTOTest {

    @Test
    @DisplayName("Builder creates entity with all fields")
    void builder_CreatesEntity() {
        UpdateReservationRequestDTO dto = UpdateReservationRequestDTO.builder()
                .fechaHoraInicio("2024-06-15T10:00:00")
                .comentarios("Cliente solicita cambio de horario")
                .build();

        assertThat(dto).isNotNull();
        assertThat(dto.getFechaHoraInicio()).isEqualTo("2024-06-15T10:00:00");
        assertThat(dto.getComentarios()).isEqualTo("Cliente solicita cambio de horario");
    }

    @Test
    @DisplayName("Default constructor creates empty DTO")
    void defaultConstructor() {
        UpdateReservationRequestDTO dto = new UpdateReservationRequestDTO();
        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Setters and getters work")
    void settersAndGetters() {
        UpdateReservationRequestDTO dto = new UpdateReservationRequestDTO();
        dto.setFechaHoraInicio("2024-06-20T14:30:00");
        dto.setComentarios("Updated comment");

        assertThat(dto.getFechaHoraInicio()).isEqualTo("2024-06-20T14:30:00");
        assertThat(dto.getComentarios()).isEqualTo("Updated comment");
    }

    @Test
    @DisplayName("Null comments are allowed")
    void nullComments() {
        UpdateReservationRequestDTO dto = UpdateReservationRequestDTO.builder()
                .fechaHoraInicio("2024-06-15T10:00:00")
                .comentarios(null)
                .build();

        assertThat(dto.getComentarios()).isNull();
    }

    @Test
    @DisplayName("Empty comments are allowed")
    void emptyComments() {
        UpdateReservationRequestDTO dto = UpdateReservationRequestDTO.builder()
                .fechaHoraInicio("2024-06-15T10:00:00")
                .comentarios("")
                .build();

        assertThat(dto.getComentarios()).isEmpty();
    }

    @Test
    @DisplayName("Long comments within limit")
    void longCommentsWithinLimit() {
        String longComment = "A".repeat(500);
        UpdateReservationRequestDTO dto = UpdateReservationRequestDTO.builder()
                .fechaHoraInicio("2024-06-15T10:00:00")
                .comentarios(longComment)
                .build();

        assertThat(dto.getComentarios()).hasSize(500);
    }
}