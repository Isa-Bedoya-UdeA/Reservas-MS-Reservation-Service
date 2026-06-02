package com.codefactory.reservasmsreservationservice.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EmployeeBasicInfoDTO Tests")
class EmployeeBasicInfoDTOTest {

    @Test
    @DisplayName("DTO creation")
    void dto_Created() {
        EmployeeBasicInfoDTO dto = new EmployeeBasicInfoDTO();
        dto.setId(UUID.randomUUID());
        dto.setFullName("John Doe");
        assertThat(dto).isNotNull();
    }
}