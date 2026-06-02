package com.codefactory.reservasmsreservationservice.mapper;

import com.codefactory.reservasmsreservationservice.dto.response.ReservationResponseDTO;
import com.codefactory.reservasmsreservationservice.entity.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReservationMapper Tests")
class ReservationMapperTest {

    @Test
    @DisplayName("Mapper interface exists")
    void mapper_Exists() {
        ReservationMapperImpl mapper = new ReservationMapperImpl();
        assertThat(mapper).isNotNull();
    }

    @Test
    @DisplayName("Reservation entity creation")
    void reservationEntity_Created() {
        Reservation reservation = new Reservation();
        reservation.setIdReserva(java.util.UUID.randomUUID());
        assertThat(reservation).isNotNull();
    }
}