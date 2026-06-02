package com.codefactory.reservasmsreservationservice.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Reservation Entity Tests")
class ReservationEntityTest {

    @Test
    @DisplayName("Entity creation with builder")
    void entity_Created() {
        Reservation reservation = Reservation.builder()
                .idReserva(UUID.randomUUID())
                .idCliente(UUID.randomUUID())
                .idServicio(UUID.randomUUID())
                .idEmpleado(UUID.randomUUID())
                .idProveedor(UUID.randomUUID())
                .fechaHoraInicio(OffsetDateTime.now())
                .build();
        assertThat(reservation).isNotNull();
    }

    @Test
    @DisplayName("Setters and getters work")
    void settersAndGetters() {
        Reservation reservation = new Reservation();
        UUID id = UUID.randomUUID();
        reservation.setIdReserva(id);
        assertThat(reservation.getIdReserva()).isEqualTo(id);
    }
}