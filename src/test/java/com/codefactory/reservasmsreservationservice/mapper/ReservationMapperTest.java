package com.codefactory.reservasmsreservationservice.mapper;

import com.codefactory.reservasmsreservationservice.dto.external.ExternalClientDTO;
import com.codefactory.reservasmsreservationservice.dto.external.ExternalProviderDTO;
import com.codefactory.reservasmsreservationservice.dto.external.ExternalServiceDTO;
import com.codefactory.reservasmsreservationservice.dto.response.EmployeeBasicInfoDTO;
import com.codefactory.reservasmsreservationservice.dto.response.ReservationResponseDTO;
import com.codefactory.reservasmsreservationservice.entity.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReservationMapper Tests")
class ReservationMapperTest {

    private ReservationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ReservationMapper.class);
    }

    private Reservation createTestReservation() {
        Reservation reservation = new Reservation();
        reservation.setIdReserva(UUID.randomUUID());
        reservation.setIdCliente(UUID.randomUUID());
        reservation.setIdServicio(UUID.randomUUID());
        reservation.setIdEmpleado(UUID.randomUUID());
        reservation.setIdProveedor(UUID.randomUUID());
        reservation.setFechaHoraInicio(OffsetDateTime.now());
        reservation.setFechaHoraFin(OffsetDateTime.now().plusHours(1));
        reservation.setEstado(Reservation.ReservationStatus.PENDIENTE);
        reservation.setFechaCreacion(OffsetDateTime.now());
        reservation.setComentarios("Test comment");
        return reservation;
    }

    @Test
    @DisplayName("toResponseDTO from Reservation")
    void toResponseDTO_FromReservation() {
        Reservation reservation = createTestReservation();

        ReservationResponseDTO dto = mapper.toResponseDTO(reservation);

        assertThat(dto).isNotNull();
        assertThat(dto.getIdReserva()).isEqualTo(reservation.getIdReserva());
        assertThat(dto.getIdCliente()).isEqualTo(reservation.getIdCliente());
        assertThat(dto.getIdServicio()).isEqualTo(reservation.getIdServicio());
        assertThat(dto.getIdEmpleado()).isEqualTo(reservation.getIdEmpleado());
        assertThat(dto.getIdProveedor()).isEqualTo(reservation.getIdProveedor());
        assertThat(dto.getEstado()).isEqualTo("PENDIENTE");
        assertThat(dto.getComentarios()).isEqualTo(reservation.getComentarios());
    }

    @Test
    @DisplayName("toResponseDTO with all external DTOs")
    void toResponseDTO_WithAllDTOs() {
        Reservation reservation = createTestReservation();
        
        ExternalClientDTO cliente = new ExternalClientDTO();
        cliente.setId(reservation.getIdCliente());
        cliente.setNombre("Juan Pérez");
        cliente.setEmail("juan@test.com");
        
        ExternalServiceDTO servicio = new ExternalServiceDTO();
        servicio.setId(reservation.getIdServicio());
        servicio.setNombreServicio("Corte de cabello");
        servicio.setDuracionMinutos(60);
        
        ExternalProviderDTO proveedor = new ExternalProviderDTO();
        proveedor.setId(reservation.getIdProveedor());
        proveedor.setNombreComercial("Barbería Central");
        
        EmployeeBasicInfoDTO employeeInfo = new EmployeeBasicInfoDTO();
        employeeInfo.setId(reservation.getIdEmpleado());
        employeeInfo.setFullName("Carlos López");

        ReservationResponseDTO dto = mapper.toResponseDTO(
                reservation, cliente, servicio, proveedor, employeeInfo);

        assertThat(dto).isNotNull();
        assertThat(dto.getClienteNombre()).isEqualTo("Juan Pérez");
        assertThat(dto.getClienteEmail()).isEqualTo("juan@test.com");
        assertThat(dto.getServicioNombre()).isEqualTo("Corte de cabello");
        assertThat(dto.getDuracionMinutos()).isEqualTo(60);
        assertThat(dto.getProveedorNombre()).isEqualTo("Barbería Central");
        assertThat(dto.getEmpleadoNombre()).isEqualTo("Carlos López");
    }

    @Test
    @DisplayName("toResponseDTO with null external DTOs")
    void toResponseDTO_WithNullDTOs() {
        Reservation reservation = createTestReservation();

        ReservationResponseDTO dto = mapper.toResponseDTO(
                reservation, null, null, null, null);

        assertThat(dto).isNotNull();
        assertThat(dto.getIdReserva()).isEqualTo(reservation.getIdReserva());
        assertThat(dto.getClienteNombre()).isNull();
        assertThat(dto.getServicioNombre()).isNull();
    }

    @Test
    @DisplayName("toEntity from ReservationResponseDTO")
    void toEntity_FromResponseDTO() {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setIdReserva(UUID.randomUUID());
        dto.setIdCliente(UUID.randomUUID());
        dto.setIdServicio(UUID.randomUUID());
        dto.setIdEmpleado(UUID.randomUUID());
        dto.setIdProveedor(UUID.randomUUID());
        dto.setFechaHoraInicio(OffsetDateTime.now());
        dto.setFechaHoraFin(OffsetDateTime.now().plusHours(1));
        dto.setEstado("CONFIRMADA");
        dto.setComentarios("Updated comment");

        Reservation entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getIdCliente()).isEqualTo(dto.getIdCliente());
        assertThat(entity.getIdServicio()).isEqualTo(dto.getIdServicio());
        assertThat(entity.getEstado()).isEqualTo(Reservation.ReservationStatus.CONFIRMADA);
        assertThat(entity.getComentarios()).isEqualTo(dto.getComentarios());
    }

    @Test
    @DisplayName("updateFromDTO updates existing entity")
    void updateFromDTO() {
        Reservation existing = createTestReservation();

        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setEstado("CONFIRMADA");
        dto.setComentarios("Updated via DTO");

        mapper.updateFromDTO(dto, existing);

        assertThat(existing.getEstado()).isEqualTo(Reservation.ReservationStatus.CONFIRMADA);
        assertThat(existing.getComentarios()).isEqualTo("Updated via DTO");
    }

    @Test
    @DisplayName("toResponseDTO with null Reservation")
    void toResponseDTO_NullReservation() {
        ReservationResponseDTO dto = mapper.toResponseDTO((Reservation) null);
        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("toEntity with null DTO")
    void toEntity_NullDTO() {
        Reservation entity = mapper.toEntity((ReservationResponseDTO) null);
        assertThat(entity).isNull();
    }
}