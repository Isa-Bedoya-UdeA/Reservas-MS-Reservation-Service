package com.codefactory.reservasmsreservationservice.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @Test
    @DisplayName("Full entity with all fields")
    void fullEntity() {
        UUID idReserva = UUID.randomUUID();
        UUID idCliente = UUID.randomUUID();
        UUID idServicio = UUID.randomUUID();
        UUID idEmpleado = UUID.randomUUID();
        UUID idProveedor = UUID.randomUUID();
        OffsetDateTime inicio = OffsetDateTime.now();
        OffsetDateTime fin = inicio.plusHours(1);
        OffsetDateTime cancelacion = OffsetDateTime.now();

        Reservation reservation = Reservation.builder()
                .idReserva(idReserva)
                .idCliente(idCliente)
                .idServicio(idServicio)
                .idEmpleado(idEmpleado)
                .idProveedor(idProveedor)
                .fechaHoraInicio(inicio)
                .fechaHoraFin(fin)
                .estado(Reservation.ReservationStatus.PENDIENTE)
                .fechaCancelacion(cancelacion)
                .comentarios("Cliente llegó tarde")
                .build();

        assertThat(reservation.getIdReserva()).isEqualTo(idReserva);
        assertThat(reservation.getIdCliente()).isEqualTo(idCliente);
        assertThat(reservation.getIdServicio()).isEqualTo(idServicio);
        assertThat(reservation.getIdEmpleado()).isEqualTo(idEmpleado);
        assertThat(reservation.getIdProveedor()).isEqualTo(idProveedor);
        assertThat(reservation.getFechaHoraInicio()).isEqualTo(inicio);
        assertThat(reservation.getFechaHoraFin()).isEqualTo(fin);
        assertThat(reservation.getEstado()).isEqualTo(Reservation.ReservationStatus.PENDIENTE);
        assertThat(reservation.getFechaCancelacion()).isEqualTo(cancelacion);
        assertThat(reservation.getComentarios()).isEqualTo("Cliente llegó tarde");
    }

    @Test
    @DisplayName("Default status is PENDIENTE")
    void defaultStatus() {
        Reservation reservation = Reservation.builder()
                .idCliente(UUID.randomUUID())
                .idServicio(UUID.randomUUID())
                .idEmpleado(UUID.randomUUID())
                .idProveedor(UUID.randomUUID())
                .fechaHoraInicio(OffsetDateTime.now())
                .fechaHoraFin(OffsetDateTime.now().plusHours(1))
                .build();

        assertThat(reservation.getEstado()).isEqualTo(Reservation.ReservationStatus.PENDIENTE);
    }

    @Test
    @DisplayName("Update reservation status")
    void updateStatus() {
        Reservation reservation = Reservation.builder()
                .idCliente(UUID.randomUUID())
                .idServicio(UUID.randomUUID())
                .idEmpleado(UUID.randomUUID())
                .idProveedor(UUID.randomUUID())
                .fechaHoraInicio(OffsetDateTime.now())
                .fechaHoraFin(OffsetDateTime.now().plusHours(1))
                .build();

        reservation.setEstado(Reservation.ReservationStatus.CONFIRMADA);
        assertThat(reservation.getEstado()).isEqualTo(Reservation.ReservationStatus.CONFIRMADA);
    }

    @Test
    @DisplayName("Cancel reservation")
    void cancelReservation() {
        Reservation reservation = Reservation.builder()
                .idCliente(UUID.randomUUID())
                .idServicio(UUID.randomUUID())
                .idEmpleado(UUID.randomUUID())
                .idProveedor(UUID.randomUUID())
                .fechaHoraInicio(OffsetDateTime.now())
                .fechaHoraFin(OffsetDateTime.now().plusHours(1))
                .estado(Reservation.ReservationStatus.CANCELADA)
                .build();

        reservation.setFechaCancelacion(OffsetDateTime.now());
        assertThat(reservation.getEstado()).isEqualTo(Reservation.ReservationStatus.CANCELADA);
        assertThat(reservation.getFechaCancelacion()).isNotNull();
    }

    @Test
    @DisplayName("ReservationStatus enum values")
    void statusEnumValues() {
        assertThat(Reservation.ReservationStatus.values()).isNotEmpty();
        assertThat(Reservation.ReservationStatus.PENDIENTE).isNotNull();
        assertThat(Reservation.ReservationStatus.CONFIRMADA).isNotNull();
        assertThat(Reservation.ReservationStatus.CANCELADA).isNotNull();
        assertThat(Reservation.ReservationStatus.COMPLETADA).isNotNull();
        assertThat(Reservation.ReservationStatus.NO_SHOW).isNotNull();
        assertThat(Reservation.ReservationStatus.EN_PROGRESO).isNotNull();
    }

    @Test
    @DisplayName("ReservationStatus fromString")
    void statusFromString() {
        Reservation.ReservationStatus status = Reservation.ReservationStatus.valueOf("PENDIENTE");
        assertThat(status).isEqualTo(Reservation.ReservationStatus.PENDIENTE);
    }

    @Test
    @DisplayName("canBeCancelled returns true for PENDIENTE")
    void canBeCancelled_Pendiente() {
        Reservation reservation = Reservation.builder()
                .idCliente(UUID.randomUUID())
                .idServicio(UUID.randomUUID())
                .idEmpleado(UUID.randomUUID())
                .idProveedor(UUID.randomUUID())
                .fechaHoraInicio(OffsetDateTime.now())
                .fechaHoraFin(OffsetDateTime.now().plusHours(1))
                .estado(Reservation.ReservationStatus.PENDIENTE)
                .build();

        assertThat(reservation.canBeCancelled()).isTrue();
    }

    @Test
    @DisplayName("isActive returns true for active states")
    void isActive_Confirmed() {
        Reservation reservation = Reservation.builder()
                .idCliente(UUID.randomUUID())
                .idServicio(UUID.randomUUID())
                .idEmpleado(UUID.randomUUID())
                .idProveedor(UUID.randomUUID())
                .fechaHoraInicio(OffsetDateTime.now())
                .fechaHoraFin(OffsetDateTime.now().plusHours(1))
                .estado(Reservation.ReservationStatus.CONFIRMADA)
                .build();

        assertThat(reservation.isActive()).isTrue();
    }
}