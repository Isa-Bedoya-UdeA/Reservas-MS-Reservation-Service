package com.codefactory.reservasmsreservationservice.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReservationListResponseDTO Tests")
class ReservationListResponseDTOTest {

    @Test
    @DisplayName("Builder creates DTO with all fields")
    void builder_CreatesDTO() {
        ReservationListResponseDTO dto = ReservationListResponseDTO.builder()
                .reservas(Arrays.asList())
                .total(10)
                .pagina(1)
                .tamanioPagina(20)
                .tieneSiguiente(false)
                .build();

        assertThat(dto).isNotNull();
        assertThat(dto.getTotal()).isEqualTo(10);
        assertThat(dto.getPagina()).isEqualTo(1);
        assertThat(dto.getTamanioPagina()).isEqualTo(20);
        assertThat(dto.isTieneSiguiente()).isFalse();
    }

    @Test
    @DisplayName("Default constructor creates empty DTO")
    void defaultConstructor() {
        ReservationListResponseDTO dto = new ReservationListResponseDTO();
        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Setters and getters work")
    void settersAndGetters() {
        ReservationListResponseDTO dto = new ReservationListResponseDTO();
        dto.setTotal(50);
        dto.setPagina(2);
        dto.setTamanioPagina(10);
        dto.setTieneSiguiente(true);

        assertThat(dto.getTotal()).isEqualTo(50);
        assertThat(dto.getPagina()).isEqualTo(2);
        assertThat(dto.getTamanioPagina()).isEqualTo(10);
        assertThat(dto.isTieneSiguiente()).isTrue();
    }

    @Test
    @DisplayName("Has next page when tieneSiguiente is true")
    void hasNextPage() {
        ReservationListResponseDTO dto = ReservationListResponseDTO.builder()
                .total(100)
                .pagina(1)
                .tamanioPagina(20)
                .tieneSiguiente(true)
                .build();

        assertThat(dto.isTieneSiguiente()).isTrue();
    }

    @Test
    @DisplayName("No next page when tieneSiguiente is false")
    void noNextPage() {
        ReservationListResponseDTO dto = ReservationListResponseDTO.builder()
                .total(15)
                .pagina(1)
                .tamanioPagina(20)
                .tieneSiguiente(false)
                .build();

        assertThat(dto.isTieneSiguiente()).isFalse();
    }

    @Test
    @DisplayName("DTO extends RepresentationModel")
    void extendsRepresentationModel() {
        ReservationListResponseDTO dto = new ReservationListResponseDTO();
        assertThat(dto).isInstanceOf(org.springframework.hateoas.RepresentationModel.class);
    }
}