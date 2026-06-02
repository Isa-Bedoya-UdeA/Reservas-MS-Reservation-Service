package com.codefactory.reservasmsreservationservice.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MessageResponseDTO Tests")
class MessageResponseDTOTest {

    @Test
    @DisplayName("Constructor creates DTO with all fields")
    void constructor_CreatesDTO() {
        Instant now = Instant.now();

        MessageResponseDTO dto = new MessageResponseDTO("Operación exitosa", true, now);

        assertThat(dto).isNotNull();
        assertThat(dto.getMessage()).isEqualTo("Operación exitosa");
        assertThat(dto.isSuccess()).isTrue();
        assertThat(dto.getTimestamp()).isEqualTo(now);
    }

    @Test
    @DisplayName("Setters work after construction")
    void settersWork() {
        MessageResponseDTO dto = new MessageResponseDTO("initial", false, Instant.now());
        Instant now = Instant.now();
        
        dto.setMessage("Test message");
        dto.setSuccess(true);
        dto.setTimestamp(now);

        assertThat(dto.getMessage()).isEqualTo("Test message");
        assertThat(dto.isSuccess()).isTrue();
        assertThat(dto.getTimestamp()).isEqualTo(now);
    }

    @Test
    @DisplayName("Success response")
    void successResponse() {
        MessageResponseDTO dto = new MessageResponseDTO(
                "La reserva fue creada exitosamente",
                true,
                Instant.now()
        );

        assertThat(dto.isSuccess()).isTrue();
        assertThat(dto.getMessage()).contains("exitosamente");
    }

    @Test
    @DisplayName("Error response")
    void errorResponse() {
        MessageResponseDTO dto = new MessageResponseDTO(
                "Ocurrió un error al procesar la solicitud",
                false,
                Instant.now()
        );

        assertThat(dto.isSuccess()).isFalse();
        assertThat(dto.getMessage()).contains("error");
    }
}