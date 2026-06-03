package com.codefactory.reservasmsreservationservice.exception;

import com.codefactory.reservasmsreservationservice.dto.response.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        when(request.getRequestURI()).thenReturn("/api/reservations/test");
    }

    @Test
    @DisplayName("handleReservationNotFound returns 404")
    void handleReservationNotFound() {
        ReservationNotFoundException ex = new ReservationNotFoundException("Reserva no encontrada");

        ResponseEntity<ErrorResponseDTO> response = handler.handleReservationNotFound(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(404);
        assertThat(response.getBody().getCodigoError()).isEqualTo("RESERVATION_NOT_FOUND");
    }

    @Test
    @DisplayName("handleReservationConflict returns 409")
    void handleReservationConflict() {
        ReservationConflictException ex = new ReservationConflictException("Conflicto de horarios");

        ResponseEntity<ErrorResponseDTO> response = handler.handleReservationConflict(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(409);
    }

    @Test
    @DisplayName("handleInvalidReservationState returns 400")
    void handleInvalidReservationState() {
        InvalidReservationStateException ex = new InvalidReservationStateException("Estado inválido");

        ResponseEntity<ErrorResponseDTO> response = handler.handleInvalidReservationState(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
    }

    @Test
    @DisplayName("handleReservationAccessDenied returns 403")
    void handleReservationAccessDenied() {
        ReservationAccessDeniedException ex = new ReservationAccessDeniedException("No tienes acceso");

        ResponseEntity<ErrorResponseDTO> response = handler.handleReservationAccessDenied(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(403);
    }

    @Test
    @DisplayName("handleValidation returns 400")
    void handleValidation() {
        ValidationException ex = new ValidationException("Datos inválidos");

        ResponseEntity<ErrorResponseDTO> response = handler.handleValidation(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @DisplayName("handleResourceNotFound returns 404")
    void handleResourceNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Recurso no encontrado");

        ResponseEntity<ErrorResponseDTO> response = handler.handleResourceNotFound(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoError()).isEqualTo("RESOURCE_NOT_FOUND");
    }

    @Test
    @DisplayName("handleExternalServiceException returns 503")
    void handleExternalServiceException() {
        ExternalServiceException ex = new ExternalServiceException("catalog", "Servicio caído", 503);

        ResponseEntity<ErrorResponseDTO> response = handler.handleExternalServiceException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoError()).isEqualTo("EXTERNAL_SERVICE_ERROR");
    }

    @Test
    @DisplayName("handleAuthenticationException returns 401")
    void handleAuthenticationException() {
        AuthenticationException ex = new AuthenticationException("No autenticado") {};

        ResponseEntity<ErrorResponseDTO> response = handler.handleAuthenticationException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoError()).isEqualTo("AUTHENTICATION_REQUIRED");
    }

    @Test
    @DisplayName("handleAccessDeniedException returns 403")
    void handleAccessDeniedException() {
        AccessDeniedException ex = new AccessDeniedException("Acceso denegado");

        ResponseEntity<ErrorResponseDTO> response = handler.handleAccessDeniedException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoError()).isEqualTo("ACCESS_DENIED");
    }

    @Test
    @DisplayName("handleIllegalArgument returns 400")
    void handleIllegalArgument() {
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido");

        ResponseEntity<ErrorResponseDTO> response = handler.handleIllegalArgument(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoError()).isEqualTo("INVALID_ARGUMENT");
    }

    @Test
    @DisplayName("handleGenericException returns 500")
    void handleGenericException() {
        Exception ex = new RuntimeException("Error interno");

        ResponseEntity<ErrorResponseDTO> response = handler.handleGenericException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoError()).isEqualTo("INTERNAL_ERROR");
    }

    @Test
    @DisplayName("handleBusinessException returns 400")
    void handleBusinessException() {
        BusinessException ex = new BusinessException("Error de negocio", "BUSINESS_ERROR");

        ResponseEntity<ErrorResponseDTO> response = handler.handleBusinessException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoError()).isEqualTo("BUSINESS_ERROR");
    }
}