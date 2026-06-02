package com.codefactory.reservasmsreservationservice.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AlternativeSlotsResponseDTO Tests")
class AlternativeSlotsResponseDTOTest {

    @Test
    @DisplayName("Builder creates DTO with all fields")
    void builder_CreatesDTO() {
        UUID employeeId = UUID.randomUUID();
        AlternativeSlotsResponseDTO.SlotOptionDTO slot1 = AlternativeSlotsResponseDTO.SlotOptionDTO.builder()
                .fechaHoraInicio("2024-06-15T10:00:00")
                .fechaHoraFin("2024-06-15T11:00:00")
                .build();
        AlternativeSlotsResponseDTO.SlotOptionDTO slot2 = AlternativeSlotsResponseDTO.SlotOptionDTO.builder()
                .fechaHoraInicio("2024-06-15T14:00:00")
                .fechaHoraFin("2024-06-15T15:00:00")
                .build();

        AlternativeSlotsResponseDTO dto = AlternativeSlotsResponseDTO.builder()
                .idEmpleado(employeeId)
                .empleadoNombre("Carlos López")
                .slotsDisponibles(Arrays.asList(slot1, slot2))
                .build();

        assertThat(dto).isNotNull();
        assertThat(dto.getIdEmpleado()).isEqualTo(employeeId);
        assertThat(dto.getEmpleadoNombre()).isEqualTo("Carlos López");
        assertThat(dto.getSlotsDisponibles()).hasSize(2);
    }

    @Test
    @DisplayName("Default constructor creates empty DTO")
    void defaultConstructor() {
        AlternativeSlotsResponseDTO dto = new AlternativeSlotsResponseDTO();
        assertThat(dto).isNotNull();
    }

    @Test
    @DisplayName("Setters and getters work")
    void settersAndGetters() {
        AlternativeSlotsResponseDTO dto = new AlternativeSlotsResponseDTO();
        UUID employeeId = UUID.randomUUID();
        dto.setIdEmpleado(employeeId);
        dto.setEmpleadoNombre("Test Employee");

        assertThat(dto.getIdEmpleado()).isEqualTo(employeeId);
        assertThat(dto.getEmpleadoNombre()).isEqualTo("Test Employee");
    }

    @Test
    @DisplayName("SlotOptionDTO builder works")
    void slotOptionDTO_Builder() {
        AlternativeSlotsResponseDTO.SlotOptionDTO slot = AlternativeSlotsResponseDTO.SlotOptionDTO.builder()
                .fechaHoraInicio("2024-06-15T09:00:00")
                .fechaHoraFin("2024-06-15T10:00:00")
                .build();

        assertThat(slot.getFechaHoraInicio()).isEqualTo("2024-06-15T09:00:00");
        assertThat(slot.getFechaHoraFin()).isEqualTo("2024-06-15T10:00:00");
    }

    @Test
    @DisplayName("Empty slots list is allowed")
    void emptySlotsList() {
        AlternativeSlotsResponseDTO dto = AlternativeSlotsResponseDTO.builder()
                .idEmpleado(UUID.randomUUID())
                .empleadoNombre("Test")
                .slotsDisponibles(Arrays.asList())
                .build();

        assertThat(dto.getSlotsDisponibles()).isEmpty();
    }

    @Test
    @DisplayName("DTO extends RepresentationModel")
    void extendsRepresentationModel() {
        AlternativeSlotsResponseDTO dto = new AlternativeSlotsResponseDTO();
        assertThat(dto).isInstanceOf(org.springframework.hateoas.RepresentationModel.class);
    }
}