package com.codefactory.reservasmsreservationservice.service;

import com.codefactory.reservasmsreservationservice.client.AuthClientWrapper;
import com.codefactory.reservasmsreservationservice.client.CatalogClientWrapper;
import com.codefactory.reservasmsreservationservice.client.ScheduleClientWrapper;
import com.codefactory.reservasmsreservationservice.dto.external.ExternalClientDTO;
import com.codefactory.reservasmsreservationservice.dto.external.ExternalProviderDTO;
import com.codefactory.reservasmsreservationservice.dto.external.ExternalServiceDTO;
import com.codefactory.reservasmsreservationservice.dto.request.CancelReservationRequestDTO;
import com.codefactory.reservasmsreservationservice.dto.request.ChangeReservationStatusRequestDTO;
import com.codefactory.reservasmsreservationservice.dto.request.CreateReservationRequestDTO;
import com.codefactory.reservasmsreservationservice.dto.request.UpdateReservationRequestDTO;
import com.codefactory.reservasmsreservationservice.dto.response.EmployeeBasicInfoDTO;
import com.codefactory.reservasmsreservationservice.dto.response.ReservationListResponseDTO;
import com.codefactory.reservasmsreservationservice.dto.response.ReservationResponseDTO;
import com.codefactory.reservasmsreservationservice.entity.Reservation;
import com.codefactory.reservasmsreservationservice.exception.*;
import com.codefactory.reservasmsreservationservice.mapper.ReservationMapper;
import com.codefactory.reservasmsreservationservice.repository.ReservationRepository;
import com.codefactory.reservasmsreservationservice.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReservationServiceImpl using Mockito.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MS-Reservation - ReservationServiceImpl (Unit)")
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private AuthClientWrapper authClientWrapper;

    @Mock
    private CatalogClientWrapper catalogClientWrapper;

    @Mock
    private ScheduleClientWrapper scheduleClientWrapper;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private UUID clienteId;
    private UUID empleadoId;
    private UUID proveedorId;
    private UUID servicioId;
    private UUID reservaId;
    private Reservation reserva;
    private ExternalClientDTO cliente;
    private ExternalServiceDTO servicio;
    private ExternalProviderDTO proveedor;
    private EmployeeBasicInfoDTO employeeInfo;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        empleadoId = UUID.randomUUID();
        proveedorId = UUID.randomUUID();
        servicioId = UUID.randomUUID();
        reservaId = UUID.randomUUID();

        OffsetDateTime manana = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1);

        reserva = Reservation.builder()
                .idReserva(reservaId)
                .idCliente(clienteId)
                .idEmpleado(empleadoId)
                .idProveedor(proveedorId)
                .idServicio(servicioId)
                .fechaHoraInicio(manana)
                .fechaHoraFin(manana.plusHours(1))
                .estado(Reservation.ReservationStatus.CONFIRMADA)
                .comentarios("Test")
                .build();

        cliente = ExternalClientDTO.builder()
                .id(clienteId)
                .nombre("Juan Pérez")
                .email("juan@test.com")
                .activo(true)
                .build();

        servicio = ExternalServiceDTO.builder()
                .id(servicioId)
                .nombreServicio("Corte de cabello")
                .duracionMinutos(60)
                .precio(BigDecimal.valueOf(25000))
                .activo(true)
                .build();

        proveedor = ExternalProviderDTO.builder()
                .id(proveedorId)
                .nombreComercial("Barbería Test")
                .activo(true)
                .build();

        employeeInfo = EmployeeBasicInfoDTO.builder()
                .id(empleadoId)
                .fullName("Carlos Pérez")
                .build();
    }

    @Nested
    @DisplayName("createReservation")
    class CreateReservationTests {

        @Test
        @DisplayName("Debe crear reserva exitosamente")
        void createReservation_ValidRequest_ReturnsReservation() {
            // Given
            OffsetDateTime manana = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1);
            CreateReservationRequestDTO request = CreateReservationRequestDTO.builder()
                    .idServicio(servicioId)
                    .idEmpleado(empleadoId)
                    .fechaHoraInicio(manana.toString())
                    .build();

            ReservationResponseDTO expectedResponse = ReservationResponseDTO.builder()
                    .idReserva(reservaId)
                    .build();

            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            doNothing().when(scheduleClientWrapper).validateEmployee(empleadoId);
            when(scheduleClientWrapper.getEmployeeProviderId(empleadoId)).thenReturn(proveedorId);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(reservationRepository.existsConflictingReservation(eq(empleadoId), any(), any())).thenReturn(false);
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reserva);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any())).thenReturn(expectedResponse);

            // When
            ReservationResponseDTO result = reservationService.createReservation(clienteId, request);

            // Then
            assertThat(result).isNotNull();
            verify(reservationRepository).save(any(Reservation.class));
            verify(scheduleClientWrapper).createReservationBlock(any(), any(), any(), any(), any());
        }

        @Test
        @DisplayName("Debe lanzar excepción si cliente no está activo")
        void createReservation_ClienteInactivo_ThrowsException() {
            // Given
            cliente.setActivo(false);
            CreateReservationRequestDTO request = CreateReservationRequestDTO.builder()
                    .idServicio(servicioId)
                    .idEmpleado(empleadoId)
                    .fechaHoraInicio(OffsetDateTime.now().plusDays(1).toString())
                    .build();

            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);

            // When/Then
            assertThatThrownBy(() -> reservationService.createReservation(clienteId, request))
                    .isInstanceOf(ValidationException.class)
                    .hasMessageContaining("cliente no está activo");
        }

        @Test
        @DisplayName("Debe lanzar excepción si servicio no está activo")
        void createReservation_ServicioInactivo_ThrowsException() {
            // Given
            servicio.setActivo(false);
            CreateReservationRequestDTO request = CreateReservationRequestDTO.builder()
                    .idServicio(servicioId)
                    .idEmpleado(empleadoId)
                    .fechaHoraInicio(OffsetDateTime.now().plusDays(1).toString())
                    .build();

            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);

            // When/Then
            assertThatThrownBy(() -> reservationService.createReservation(clienteId, request))
                    .isInstanceOf(ValidationException.class)
                    .hasMessageContaining("servicio no está disponible");
        }

        @Test
        @DisplayName("Debe lanzar excepción si proveedor no está activo")
        void createReservation_ProveedorInactivo_ThrowsException() {
            // Given
            proveedor.setActivo(false);
            CreateReservationRequestDTO request = CreateReservationRequestDTO.builder()
                    .idServicio(servicioId)
                    .idEmpleado(empleadoId)
                    .fechaHoraInicio(OffsetDateTime.now().plusDays(1).toString())
                    .build();

            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            doNothing().when(scheduleClientWrapper).validateEmployee(empleadoId);
            when(scheduleClientWrapper.getEmployeeProviderId(empleadoId)).thenReturn(proveedorId);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);

            // When/Then
            assertThatThrownBy(() -> reservationService.createReservation(clienteId, request))
                    .isInstanceOf(ValidationException.class)
                    .hasMessageContaining("proveedor no está activo");
        }

        @Test
        @DisplayName("Debe lanzar excepción si hay conflicto de horarios")
        void createReservation_HorarioOcupado_ThrowsException() {
            // Given
            CreateReservationRequestDTO request = CreateReservationRequestDTO.builder()
                    .idServicio(servicioId)
                    .idEmpleado(empleadoId)
                    .fechaHoraInicio(OffsetDateTime.now().plusDays(1).toString())
                    .build();

            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            doNothing().when(scheduleClientWrapper).validateEmployee(empleadoId);
            when(scheduleClientWrapper.getEmployeeProviderId(empleadoId)).thenReturn(proveedorId);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(reservationRepository.existsConflictingReservation(eq(empleadoId), any(), any())).thenReturn(true);

            // When/Then
            assertThatThrownBy(() -> reservationService.createReservation(clienteId, request))
                    .isInstanceOf(ReservationConflictException.class);
        }
    }

    @Nested
    @DisplayName("getReservationById")
    class GetReservationByIdTests {

        @Test
        @DisplayName("Debe obtener reserva por ID")
        void getReservationById_ExistingReservation_ReturnsReservation() {
            // Given
            ReservationResponseDTO expected = ReservationResponseDTO.builder().idReserva(reservaId).build();
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any())).thenReturn(expected);

            // When
            ReservationResponseDTO result = reservationService.getReservationById(reservaId);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getIdReserva()).isEqualTo(reservaId);
        }

        @Test
        @DisplayName("Debe lanzar excepción si reserva no existe")
        void getReservationById_NotFound_ThrowsException() {
            // Given
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.empty());

            // When/Then
            assertThatThrownBy(() -> reservationService.getReservationById(reservaId))
                    .isInstanceOf(ReservationNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("getReservationsByClient")
    class GetReservationsByClientTests {

        @Test
        @DisplayName("Debe obtener reservas de un cliente")
        void getReservationsByClient_ReturnsReservations() {
            // Given
            ReservationResponseDTO reservaDto = ReservationResponseDTO.builder()
                    .idReserva(reservaId)
                    .clienteNombre("Juan Pérez")
                    .build();

            when(reservationRepository.findByIdClienteOrderByFechaHoraInicioDesc(clienteId))
                    .thenReturn(List.of(reserva));
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(reserva, cliente, servicio, proveedor, employeeInfo))
                    .thenReturn(reservaDto);

            // When
            ReservationListResponseDTO result = reservationService.getReservationsByClient(clienteId, null, 0, 10);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getReservas()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("cancelReservation")
    class CancelReservationTests {

        @Test
        @DisplayName("Debe cancelar reserva exitosamente")
        void cancelReservation_ValidReservation_Cancels() {
            // Given
            reserva.setEstado(Reservation.ReservationStatus.CONFIRMADA);
            CancelReservationRequestDTO request = CancelReservationRequestDTO.builder()
                    .comentariosCancelacion("Cliente canceló")
                    .build();

            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reserva);
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any()))
                    .thenReturn(ReservationResponseDTO.builder().idReserva(reservaId).build());

            // When
            ReservationResponseDTO result = reservationService.cancelReservation(clienteId, reservaId, request);

            // Then
            assertThat(result).isNotNull();
            verify(scheduleClientWrapper).cancelReservationBlock(reservaId);
        }

        @Test
        @DisplayName("Debe lanzar excepción si no es dueño de la reserva")
        void cancelReservation_NotOwner_ThrowsException() {
            // Given
            UUID otherClient = UUID.randomUUID();
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

            // When/Then
            assertThatThrownBy(() -> reservationService.cancelReservation(otherClient, reservaId, null))
                    .isInstanceOf(ReservationAccessDeniedException.class);
        }

        @Test
        @DisplayName("Debe lanzar excepción si reserva ya fue completada")
        void cancelReservation_AlreadyCompleted_ThrowsException() {
            // Given
            reserva.setEstado(Reservation.ReservationStatus.COMPLETADA);
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

            // When/Then
            assertThatThrownBy(() -> reservationService.cancelReservation(clienteId, reservaId, null))
                    .isInstanceOf(InvalidReservationStateException.class);
        }
    }

    @Nested
    @DisplayName("changeReservationStatus")
    class ChangeReservationStatusTests {

        @Test
        @DisplayName("Debe cambiar estado exitosamente")
        void changeReservationStatus_ValidTransition_ChangesStatus() {
            // Given
            ChangeReservationStatusRequestDTO request = ChangeReservationStatusRequestDTO.builder()
                    .estado("COMPLETADA")
                    .build();

            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reserva);
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any()))
                    .thenReturn(ReservationResponseDTO.builder().idReserva(reservaId).build());

            // When
            ReservationResponseDTO result = reservationService.changeReservationStatus(reservaId, request);

            // Then
            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Debe lanzar excepción para transición inválida")
        void changeReservationStatus_InvalidTransition_ThrowsException() {
            // Given
            reserva.setEstado(Reservation.ReservationStatus.CANCELADA);
            ChangeReservationStatusRequestDTO request = ChangeReservationStatusRequestDTO.builder()
                    .estado("COMPLETADA")
                    .build();

            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

            // When/Then
            assertThatThrownBy(() -> reservationService.changeReservationStatus(reservaId, request))
                    .isInstanceOf(InvalidReservationStateException.class);
        }
    }

    @Nested
    @DisplayName("checkAvailability")
    class CheckAvailabilityTests {

        @Test
        @DisplayName("Debe retornar true cuando hay disponibilidad")
        void checkAvailability_Available_ReturnsTrue() {
            // Given
            String fechaInicio = OffsetDateTime.now().plusDays(1).toString();
            String fechaFin = OffsetDateTime.now().plusDays(1).plusHours(1).toString();

            when(reservationRepository.existsConflictingReservation(eq(empleadoId), any(), any()))
                    .thenReturn(false);

            // When
            boolean result = reservationService.checkAvailability(empleadoId, fechaInicio, fechaFin);

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Debe retornar false cuando no hay disponibilidad")
        void checkAvailability_NotAvailable_ReturnsFalse() {
            // Given
            String fechaInicio = OffsetDateTime.now().plusDays(1).toString();
            String fechaFin = OffsetDateTime.now().plusDays(1).plusHours(1).toString();

            when(reservationRepository.existsConflictingReservation(eq(empleadoId), any(), any()))
                    .thenReturn(true);

            // When
            boolean result = reservationService.checkAvailability(empleadoId, fechaInicio, fechaFin);

            // Then
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("getReservationsByProvider")
    class GetReservationsByProviderTests {

        @Test
        @DisplayName("Debe obtener reservas del proveedor filtrando por estado")
        void getReservationsByProvider_WithEstado_ReturnsFiltered() {
            // Given
            ReservationResponseDTO dto = ReservationResponseDTO.builder().idReserva(reservaId).build();
            when(reservationRepository.findByIdProveedorAndEstado(proveedorId, Reservation.ReservationStatus.CONFIRMADA))
                    .thenReturn(List.of(reserva));
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any())).thenReturn(dto);

            // When
            ReservationListResponseDTO result = reservationService.getReservationsByProvider(proveedorId, "CONFIRMADA", 0, 10);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getReservas()).hasSize(1);
        }

        @Test
        @DisplayName("Debe obtener reservas del proveedor sin filtro de estado")
        void getReservationsByProvider_WithoutEstado_ReturnsAll() {
            // Given
            ReservationResponseDTO dto = ReservationResponseDTO.builder().idReserva(reservaId).build();
            when(reservationRepository.findByIdProveedorOrderByFechaHoraInicioDesc(proveedorId))
                    .thenReturn(List.of(reserva));
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any())).thenReturn(dto);

            // When
            ReservationListResponseDTO result = reservationService.getReservationsByProvider(proveedorId, null, 0, 10);

            // Then
            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Debe lanzar ValidationException con estado inválido")
        void getReservationsByProvider_InvalidEstado_ThrowsException() {
            // When/Then
            assertThatThrownBy(() -> reservationService.getReservationsByProvider(proveedorId, "INVALIDO", 0, 10))
                    .isInstanceOf(ValidationException.class)
                    .hasMessageContaining("Estado inválido");
        }
    }

    @Nested
    @DisplayName("getReservationsByEmployee")
    class GetReservationsByEmployeeTests {

        @Test
        @DisplayName("Debe obtener reservas del empleado filtrando por estado")
        void getReservationsByEmployee_WithEstado_ReturnsFiltered() {
            // Given
            ReservationResponseDTO dto = ReservationResponseDTO.builder().idReserva(reservaId).build();
            when(reservationRepository.findByIdEmpleadoAndEstado(empleadoId, Reservation.ReservationStatus.CONFIRMADA))
                    .thenReturn(List.of(reserva));
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any())).thenReturn(dto);

            // When
            ReservationListResponseDTO result = reservationService.getReservationsByEmployee(empleadoId, "CONFIRMADA", 0, 10);

            // Then
            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Debe obtener reservas del empleado sin filtro de estado")
        void getReservationsByEmployee_WithoutEstado_ReturnsAll() {
            // Given
            ReservationResponseDTO dto = ReservationResponseDTO.builder().idReserva(reservaId).build();
            when(reservationRepository.findByIdEmpleadoOrderByFechaHoraInicioAsc(empleadoId))
                    .thenReturn(List.of(reserva));
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any())).thenReturn(dto);

            // When
            ReservationListResponseDTO result = reservationService.getReservationsByEmployee(empleadoId, null, 0, 10);

            // Then
            assertThat(result).isNotNull();
        }
    }

    @Nested
    @DisplayName("updateReservation")
    class UpdateReservationTests {

        @Test
        @DisplayName("Debe actualizar reserva con cambio de fecha")
        void updateReservation_WithDateChange_UpdatesSuccessfully() {
            // Given
            OffsetDateTime nuevaFecha = OffsetDateTime.now(ZoneOffset.UTC).plusDays(2);
            UpdateReservationRequestDTO request = UpdateReservationRequestDTO.builder()
                    .fechaHoraInicio(nuevaFecha.toString())
                    .comentarios("Nueva nota")
                    .build();

            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(reservationRepository.existsConflictingReservation(eq(empleadoId), any(), any())).thenReturn(false);
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reserva);
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any()))
                    .thenReturn(ReservationResponseDTO.builder().idReserva(reservaId).build());

            // When
            ReservationResponseDTO result = reservationService.updateReservation(clienteId, reservaId, request);

            // Then
            assertThat(result).isNotNull();
            verify(reservationRepository).save(any(Reservation.class));
        }

        @Test
        @DisplayName("Debe actualizar reserva solo con comentarios")
        void updateReservation_OnlyComments_UpdatesSuccessfully() {
            // Given
            UpdateReservationRequestDTO request = UpdateReservationRequestDTO.builder()
                    .comentarios("Solo un comentario")
                    .build();

            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reserva);
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any()))
                    .thenReturn(ReservationResponseDTO.builder().idReserva(reservaId).build());

            // When
            ReservationResponseDTO result = reservationService.updateReservation(clienteId, reservaId, request);

            // Then
            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Debe lanzar excepción si la reserva no existe")
        void updateReservation_NotFound_ThrowsException() {
            // Given
            UpdateReservationRequestDTO request = UpdateReservationRequestDTO.builder().build();
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.empty());

            // When/Then
            assertThatThrownBy(() -> reservationService.updateReservation(clienteId, reservaId, request))
                    .isInstanceOf(ReservationNotFoundException.class);
        }

        @Test
        @DisplayName("Debe lanzar excepción si el cliente no es el dueño")
        void updateReservation_NotOwner_ThrowsException() {
            // Given
            UUID otherClient = UUID.randomUUID();
            UpdateReservationRequestDTO request = UpdateReservationRequestDTO.builder().build();
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

            // When/Then
            assertThatThrownBy(() -> reservationService.updateReservation(otherClient, reservaId, request))
                    .isInstanceOf(ReservationAccessDeniedException.class);
        }

        @Test
        @DisplayName("Debe lanzar excepción si reserva no está activa")
        void updateReservation_NotActive_ThrowsException() {
            // Given
            reserva.setEstado(Reservation.ReservationStatus.CANCELADA);
            UpdateReservationRequestDTO request = UpdateReservationRequestDTO.builder().build();
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

            // When/Then
            assertThatThrownBy(() -> reservationService.updateReservation(clienteId, reservaId, request))
                    .isInstanceOf(InvalidReservationStateException.class);
        }

        @Test
        @DisplayName("Debe lanzar ReservationConflictException si nueva fecha tiene conflicto")
        void updateReservation_Conflict_ThrowsException() {
            // Given
            OffsetDateTime nuevaFecha = OffsetDateTime.now(ZoneOffset.UTC).plusDays(2);
            UpdateReservationRequestDTO request = UpdateReservationRequestDTO.builder()
                    .fechaHoraInicio(nuevaFecha.toString())
                    .build();

            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(reservationRepository.existsConflictingReservation(eq(empleadoId), any(), any())).thenReturn(true);

            // When/Then
            assertThatThrownBy(() -> reservationService.updateReservation(clienteId, reservaId, request))
                    .isInstanceOf(ReservationConflictException.class);
        }
    }

    @Nested
    @DisplayName("getAlternativeSlots")
    class GetAlternativeSlotsTests {

        @Test
        @DisplayName("Debe retornar slots alternativos vacíos")
        void getAlternativeSlots_ReturnsEmptySlots() {
            // When
            com.codefactory.reservasmsreservationservice.dto.response.AlternativeSlotsResponseDTO result =
                    reservationService.getAlternativeSlots(servicioId, empleadoId, OffsetDateTime.now().plusDays(1).toString());

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getIdEmpleado()).isEqualTo(empleadoId);
            assertThat(result.getSlotsDisponibles()).isEmpty();
        }
    }

    @Nested
    @DisplayName("createReservation - validaciones adicionales")
    class CreateReservationAdditionalTests {

        @Test
        @DisplayName("Debe lanzar excepción si idEmpleado es null")
        void createReservation_NullEmpleado_ThrowsException() {
            // Given
            CreateReservationRequestDTO request = CreateReservationRequestDTO.builder()
                    .idServicio(servicioId)
                    .fechaHoraInicio(OffsetDateTime.now().plusDays(1).toString())
                    .build();

            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);

            // When/Then
            assertThatThrownBy(() -> reservationService.createReservation(clienteId, request))
                    .isInstanceOf(ValidationException.class)
                    .hasMessageContaining("empleado");
        }

        @Test
        @DisplayName("Debe lanzar excepción si fecha es pasada")
        void createReservation_PastDate_ThrowsException() {
            // Given
            CreateReservationRequestDTO request = CreateReservationRequestDTO.builder()
                    .idServicio(servicioId)
                    .idEmpleado(empleadoId)
                    .fechaHoraInicio(OffsetDateTime.now().minusDays(1).toString())
                    .build();

            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            doNothing().when(scheduleClientWrapper).validateEmployee(empleadoId);
            when(scheduleClientWrapper.getEmployeeProviderId(empleadoId)).thenReturn(proveedorId);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);

            // When/Then
            assertThatThrownBy(() -> reservationService.createReservation(clienteId, request))
                    .isInstanceOf(ValidationException.class)
                    .hasMessageContaining("futura");
        }

        @Test
        @DisplayName("Debe lanzar ValidationException por formato de fecha inválido")
        void createReservation_InvalidDateFormat_ThrowsException() {
            // Given
            CreateReservationRequestDTO request = CreateReservationRequestDTO.builder()
                    .idServicio(servicioId)
                    .idEmpleado(empleadoId)
                    .fechaHoraInicio("not-a-date")
                    .build();

            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            doNothing().when(scheduleClientWrapper).validateEmployee(empleadoId);
            when(scheduleClientWrapper.getEmployeeProviderId(empleadoId)).thenReturn(proveedorId);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);

            // When/Then
            assertThatThrownBy(() -> reservationService.createReservation(clienteId, request))
                    .isInstanceOf(ValidationException.class)
                    .hasMessageContaining("Formato de fecha inválido");
        }
    }

    @Nested
    @DisplayName("changeReservationStatus - paths adicionales")
    class ChangeReservationStatusAdditionalTests {

        @Test
        @DisplayName("Debe lanzar ValidationException si estado no existe")
        void changeReservationStatus_InvalidEstado_ThrowsException() {
            // Given
            ChangeReservationStatusRequestDTO request = ChangeReservationStatusRequestDTO.builder()
                    .estado("NO_EXISTE")
                    .build();
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

            // When/Then
            assertThatThrownBy(() -> reservationService.changeReservationStatus(reservaId, request))
                    .isInstanceOf(ValidationException.class);
        }

        @Test
        @DisplayName("Debe cambiar estado y guardar con comentarios")
        void changeReservationStatus_WithComments_UpdatesComments() {
            // Given
            ChangeReservationStatusRequestDTO request = ChangeReservationStatusRequestDTO.builder()
                    .estado("COMPLETADA")
                    .comentarios("Finalizado correctamente")
                    .build();

            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reserva);
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any()))
                    .thenReturn(ReservationResponseDTO.builder().idReserva(reservaId).build());

            // When
            ReservationResponseDTO result = reservationService.changeReservationStatus(reservaId, request);

            // Then
            assertThat(result).isNotNull();
        }
    }

    @Nested
    @DisplayName("cancelReservation - paths adicionales")
    class CancelReservationAdditionalTests {

        @Test
        @DisplayName("Debe cancelar sin comentarios de cancelación")
        void cancelReservation_NoComments_Cancels() {
            // Given
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reserva);
            when(authClientWrapper.getClientOrThrow(clienteId)).thenReturn(cliente);
            when(catalogClientWrapper.getServiceOrThrow(servicioId)).thenReturn(servicio);
            when(authClientWrapper.getProviderOrThrow(proveedorId)).thenReturn(proveedor);
            when(scheduleClientWrapper.getEmployeeBasicInfo(empleadoId)).thenReturn(employeeInfo);
            when(reservationMapper.toResponseDTO(any(), any(), any(), any(), any()))
                    .thenReturn(ReservationResponseDTO.builder().idReserva(reservaId).build());

            // When
            ReservationResponseDTO result = reservationService.cancelReservation(clienteId, reservaId, null);

            // Then
            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Debe lanzar NotFoundException si reserva no existe")
        void cancelReservation_NotFound_ThrowsException() {
            // Given
            when(reservationRepository.findById(reservaId)).thenReturn(Optional.empty());

            // When/Then
            assertThatThrownBy(() -> reservationService.cancelReservation(clienteId, reservaId, null))
                    .isInstanceOf(ReservationNotFoundException.class);
        }
    }
}