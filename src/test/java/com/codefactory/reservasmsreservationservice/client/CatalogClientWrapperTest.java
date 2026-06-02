package com.codefactory.reservasmsreservationservice.client;

import com.codefactory.reservasmsreservationservice.dto.external.ExternalServiceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CatalogClientWrapper Tests")
class CatalogClientWrapperTest {

    @Mock
    private CatalogClient catalogClient;

    private CatalogClientWrapper catalogClientWrapper;

    @BeforeEach
    void setUp() {
        catalogClientWrapper = new CatalogClientWrapper(catalogClient);
    }

    @Test
    @DisplayName("getServiceOrThrow returns service when found")
    void getServiceOrThrow_Success() {
        UUID serviceId = UUID.randomUUID();
        ExternalServiceDTO service = new ExternalServiceDTO();
        service.setId(serviceId);
        service.setNombreServicio("Corte de cabello");
        service.setActivo(true);
        service.setDuracionMinutos(60);

        when(catalogClient.getServiceById(serviceId)).thenReturn(ResponseEntity.ok(service));

        ExternalServiceDTO result = catalogClientWrapper.getServiceOrThrow(serviceId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(serviceId);
        assertThat(result.getNombreServicio()).isEqualTo("Corte de cabello");
        assertThat(result.isActivo()).isTrue();
        assertThat(result.getDuracionMinutos()).isEqualTo(60);
        verify(catalogClient).getServiceById(serviceId);
    }

    @Test
    @DisplayName("getServiceOrThrow returns inactive service")
    void getServiceOrThrow_InactiveService() {
        UUID serviceId = UUID.randomUUID();
        ExternalServiceDTO service = new ExternalServiceDTO();
        service.setId(serviceId);
        service.setNombreServicio("Test Service");
        service.setActivo(false);

        when(catalogClient.getServiceById(serviceId)).thenReturn(ResponseEntity.ok(service));

        ExternalServiceDTO result = catalogClientWrapper.getServiceOrThrow(serviceId);
        assertThat(result.isActivo()).isFalse();
    }

    @Test
    @DisplayName("validateService passes for active service")
    void validateService_ActiveService() {
        UUID serviceId = UUID.randomUUID();
        ExternalServiceDTO service = new ExternalServiceDTO();
        service.setId(serviceId);
        service.setActivo(true);

        when(catalogClient.getServiceById(serviceId)).thenReturn(ResponseEntity.ok(service));

        catalogClientWrapper.validateService(serviceId);
        verify(catalogClient).getServiceById(serviceId);
    }

    @Test
    @DisplayName("evictServiceCache does not throw")
    void evictServiceCache_NoException() {
        UUID serviceId = UUID.randomUUID();
        catalogClientWrapper.evictServiceCache(serviceId);
    }
}