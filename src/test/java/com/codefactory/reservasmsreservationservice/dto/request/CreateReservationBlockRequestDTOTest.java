package com.codefactory.reservasmsreservationservice.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CreateReservationBlockRequestDTO Tests")
class CreateReservationBlockRequestDTOTest {

    @Test
    @DisplayName("Builder creates entity with all fields")
    void builder_CreatesEntity() {
        UUID employeeId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();
        LocalDate date = LocalDate.now().plusDays(7);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        CreateReservationBlockRequestDTO dto = CreateReservationBlockRequestDTO.builder()
                .employeeId(employeeId)
                .reservationId(reservationId)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        assertThat(dto).isNotNull();
        assertThat(dto.getEmployeeId()).isEqualTo(employeeId);
        assertThat(dto.getReservationId()).isEqualTo(reservationId);
        assertThat(dto.getDate()).isEqualTo(date);
        assertThat(dto.getStartTime()).isEqualTo(startTime);
        assertThat(dto.getEndTime()).isEqualTo(endTime);
    }

    @Test
    @DisplayName("Default constructor creates empty DTO")
    void defaultConstructor() {
        CreateReservationBlockRequestDTO dto = new CreateReservationBlockRequestDTO();
        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Setters and getters work")
    void settersAndGetters() {
        UUID employeeId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime startTime = LocalTime.of(14, 0);
        LocalTime endTime = LocalTime.of(15, 30);

        CreateReservationBlockRequestDTO dto = new CreateReservationBlockRequestDTO();
        dto.setEmployeeId(employeeId);
        dto.setReservationId(reservationId);
        dto.setDate(date);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);

        assertThat(dto.getEmployeeId()).isEqualTo(employeeId);
        assertThat(dto.getReservationId()).isEqualTo(reservationId);
        assertThat(dto.getDate()).isEqualTo(date);
        assertThat(dto.getStartTime()).isEqualTo(startTime);
        assertThat(dto.getEndTime()).isEqualTo(endTime);
    }

    @Test
    @DisplayName("Morning block reservation")
    void morningBlockReservation() {
        UUID employeeId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(9, 0);

        CreateReservationBlockRequestDTO dto = CreateReservationBlockRequestDTO.builder()
                .employeeId(employeeId)
                .reservationId(reservationId)
                .date(LocalDate.of(2024, 6, 15))
                .startTime(startTime)
                .endTime(endTime)
                .build();

        assertThat(dto.getStartTime().getHour()).isLessThan(12);
        assertThat(endTime.isAfter(startTime)).isTrue();
    }

    @Test
    @DisplayName("Afternoon block reservation")
    void afternoonBlockReservation() {
        UUID employeeId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();

        CreateReservationBlockRequestDTO dto = CreateReservationBlockRequestDTO.builder()
                .employeeId(employeeId)
                .reservationId(reservationId)
                .date(LocalDate.of(2024, 6, 15))
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(15, 0))
                .build();

        assertThat(dto.getStartTime().getHour()).isGreaterThanOrEqualTo(12);
    }
}