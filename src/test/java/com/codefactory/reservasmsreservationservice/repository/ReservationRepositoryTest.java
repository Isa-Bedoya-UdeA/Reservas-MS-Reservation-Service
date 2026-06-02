package com.codefactory.reservasmsreservationservice.repository;

import com.codefactory.reservasmsreservationservice.entity.Reservation;
import com.codefactory.reservasmsreservationservice.entity.Reservation.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("ReservationRepository Tests")
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Repository bean creation")
    void repository_Created() {
        assertThat(reservationRepository).isNotNull();
    }
}