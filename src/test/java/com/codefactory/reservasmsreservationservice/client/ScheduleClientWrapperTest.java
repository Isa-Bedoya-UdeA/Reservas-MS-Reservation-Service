package com.codefactory.reservasmsreservationservice.client;

import com.codefactory.reservasmsreservationservice.dto.request.CreateReservationBlockRequestDTO;
import com.codefactory.reservasmsreservationservice.dto.response.EmployeeBasicInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduleClientWrapper Tests")
class ScheduleClientWrapperTest {

    @Mock
    private ScheduleClient scheduleClient;

    private ScheduleClientWrapper scheduleClientWrapper;

    @BeforeEach
    void setUp() {
        scheduleClientWrapper = new ScheduleClientWrapper(scheduleClient);
    }

    @Test
    @DisplayName("isEmployeeActive returns true when active")
    void isEmployeeActive_True() {
        UUID employeeId = UUID.randomUUID();
        when(scheduleClient.isEmployeeActive(employeeId)).thenReturn(ResponseEntity.ok(true));

        boolean result = scheduleClientWrapper.isEmployeeActive(employeeId);

        assertThat(result).isTrue();
        verify(scheduleClient).isEmployeeActive(employeeId);
    }

    @Test
    @DisplayName("isEmployeeActive returns false when not active")
    void isEmployeeActive_False() {
        UUID employeeId = UUID.randomUUID();
        when(scheduleClient.isEmployeeActive(employeeId)).thenReturn(ResponseEntity.ok(false));

        boolean result = scheduleClientWrapper.isEmployeeActive(employeeId);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("getEmployeeProviderId returns provider ID")
    void getEmployeeProviderId_Success() {
        UUID employeeId = UUID.randomUUID();
        UUID providerId = UUID.randomUUID();
        when(scheduleClient.getEmployeeProviderId(employeeId)).thenReturn(ResponseEntity.ok(providerId));

        UUID result = scheduleClientWrapper.getEmployeeProviderId(employeeId);

        assertThat(result).isEqualTo(providerId);
    }

    @Test
    @DisplayName("isEmployeeAvailable returns true when available")
    void isEmployeeAvailable_True() {
        UUID employeeId = UUID.randomUUID();
        OffsetDateTime start = OffsetDateTime.now();
        OffsetDateTime end = start.plusHours(1);
        when(scheduleClient.checkEmployeeAvailability(employeeId, start, end)).thenReturn(ResponseEntity.ok(true));

        boolean result = scheduleClientWrapper.isEmployeeAvailable(employeeId, start, end);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("createReservationBlock does not throw on success")
    void createReservationBlock_NoException() {
        UUID reservationId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);

        when(scheduleClient.createReservationBlock(any())).thenReturn(ResponseEntity.ok().build());

        scheduleClientWrapper.createReservationBlock(reservationId, employeeId, date, startTime, endTime);

        verify(scheduleClient).createReservationBlock(any(CreateReservationBlockRequestDTO.class));
    }

    @Test
    @DisplayName("cancelReservationBlock does not throw on success")
    void cancelReservationBlock_NoException() {
        UUID reservationId = UUID.randomUUID();

        when(scheduleClient.cancelReservationBlock(reservationId)).thenReturn(ResponseEntity.ok().build());

        scheduleClientWrapper.cancelReservationBlock(reservationId);

        verify(scheduleClient).cancelReservationBlock(reservationId);
    }

    @Test
    @DisplayName("getEmployeeBasicInfo returns employee info")
    void getEmployeeBasicInfo_Success() {
        UUID employeeId = UUID.randomUUID();
        EmployeeBasicInfoDTO info = new EmployeeBasicInfoDTO();
        info.setId(employeeId);
        info.setFullName("Carlos López");

        when(scheduleClient.getEmployeeBasicInfo(employeeId)).thenReturn(ResponseEntity.ok(info));

        EmployeeBasicInfoDTO result = scheduleClientWrapper.getEmployeeBasicInfo(employeeId);

        assertThat(result).isNotNull();
        assertThat(result.getFullName()).isEqualTo("Carlos López");
    }

    @Test
    @DisplayName("validateEmployee does not throw when active")
    void validateEmployee_NoException() {
        UUID employeeId = UUID.randomUUID();
        when(scheduleClient.isEmployeeActive(employeeId)).thenReturn(ResponseEntity.ok(true));

        scheduleClientWrapper.validateEmployee(employeeId);

        verify(scheduleClient).isEmployeeActive(employeeId);
    }

    @Test
    @DisplayName("validateEmployeeBelongsToProvider passes when matching")
    void validateEmployeeBelongsToProvider_Matching() {
        UUID employeeId = UUID.randomUUID();
        UUID providerId = UUID.randomUUID();
        when(scheduleClient.getEmployeeProviderId(employeeId)).thenReturn(ResponseEntity.ok(providerId));

        scheduleClientWrapper.validateEmployeeBelongsToProvider(employeeId, providerId);

        verify(scheduleClient).getEmployeeProviderId(employeeId);
    }

    @Test
    @DisplayName("evictEmployeeCache does not throw")
    void evictEmployeeCache_NoException() {
        UUID employeeId = UUID.randomUUID();
        scheduleClientWrapper.evictEmployeeCache(employeeId);
    }
}