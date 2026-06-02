package com.codefactory.reservasmsreservationservice.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ErrorResponseDTO Tests")
class ErrorResponseDTOTest {

    @Test
    @DisplayName("Builder creates DTO with all fields")
    void builder_CreatesDTO() {
        OffsetDateTime now = OffsetDateTime.now();

        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .timestamp(now)
                .status(404)
                .error("Not Found")
                .codigoError("RESERVATION_NOT_FOUND")
                .message("La reserva no fue encontrada")
                .path("/api/reservations/123")
                .detalles("El ID proporcionado no existe en el sistema")
                .build();

        assertThat(dto).isNotNull();
        assertThat(dto.getTimestamp()).isEqualTo(now);
        assertThat(dto.getStatus()).isEqualTo(404);
        assertThat(dto.getError()).isEqualTo("Not Found");
        assertThat(dto.getCodigoError()).isEqualTo("RESERVATION_NOT_FOUND");
        assertThat(dto.getMessage()).isEqualTo("La reserva no fue encontrada");
        assertThat(dto.getPath()).isEqualTo("/api/reservations/123");
        assertThat(dto.getDetalles()).isEqualTo("El ID proporcionado no existe en el sistema");
    }

    @Test
    @DisplayName("Default constructor creates empty DTO")
    void defaultConstructor() {
        ErrorResponseDTO dto = new ErrorResponseDTO();
        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Setters and getters work")
    void settersAndGetters() {
        ErrorResponseDTO dto = new ErrorResponseDTO();
        dto.setStatus(500);
        dto.setError("Internal Server Error");
        dto.setMessage("Something went wrong");
        dto.setPath("/api/test");

        assertThat(dto.getStatus()).isEqualTo(500);
        assertThat(dto.getError()).isEqualTo("Internal Server Error");
        assertThat(dto.getMessage()).isEqualTo("Something went wrong");
        assertThat(dto.getPath()).isEqualTo("/api/test");
    }

    @Test
    @DisplayName("400 Bad Request error")
    void badRequestError() {
        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .status(400)
                .error("Bad Request")
                .codigoError("VALIDATION_ERROR")
                .message("Datos de entrada inválidos")
                .build();

        assertThat(dto.getStatus()).isEqualTo(400);
        assertThat(dto.getCodigoError()).isEqualTo("VALIDATION_ERROR");
    }

    @Test
    @DisplayName("403 Forbidden error")
    void forbiddenError() {
        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .status(403)
                .error("Forbidden")
                .codigoError("ACCESS_DENIED")
                .message("No tienes permisos para acceder a este recurso")
                .build();

        assertThat(dto.getStatus()).isEqualTo(403);
        assertThat(dto.getCodigoError()).isEqualTo("ACCESS_DENIED");
    }

    @Test
    @DisplayName("503 Service Unavailable error")
    void serviceUnavailableError() {
        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .status(503)
                .error("Service Unavailable")
                .codigoError("EXTERNAL_SERVICE_ERROR")
                .message("El servicio externo no está disponible")
                .build();

        assertThat(dto.getStatus()).isEqualTo(503);
        assertThat(dto.getCodigoError()).isEqualTo("EXTERNAL_SERVICE_ERROR");
    }

    @Test
    @DisplayName("Error without detalles is allowed")
    void errorWithoutDetalles() {
        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .status(404)
                .error("Not Found")
                .message("No encontrado")
                .build();

        assertThat(dto.getDetalles()).isNull();
    }
}