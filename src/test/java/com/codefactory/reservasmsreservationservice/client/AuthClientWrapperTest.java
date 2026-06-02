package com.codefactory.reservasmsreservationservice.client;

import com.codefactory.reservasmsreservationservice.dto.external.ExternalClientDTO;
import com.codefactory.reservasmsreservationservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthClientWrapper Tests")
class AuthClientWrapperTest {

    @Mock
    private AuthClient authClient;

    private AuthClientWrapper authClientWrapper;

    @BeforeEach
    void setUp() {
        authClientWrapper = new AuthClientWrapper(authClient);
    }

    @Test
    @DisplayName("getClientOrThrow returns client when found")
    void getClientOrThrow_Success() {
        UUID clientId = UUID.randomUUID();
        ExternalClientDTO client = new ExternalClientDTO();
        client.setId(clientId);
        client.setNombre("Test Client");
        client.setActivo(true);

        when(authClient.getClientById(clientId)).thenReturn(ResponseEntity.ok(client));

        ExternalClientDTO result = authClientWrapper.getClientOrThrow(clientId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(clientId);
        assertThat(result.getNombre()).isEqualTo("Test Client");
        assertThat(result.isActivo()).isTrue();
        verify(authClient).getClientById(clientId);
    }

    @Test
    @DisplayName("getClientOrThrow returns inactive client")
    void getClientOrThrow_InactiveClient() {
        UUID clientId = UUID.randomUUID();
        ExternalClientDTO client = new ExternalClientDTO();
        client.setId(clientId);
        client.setActivo(false);

        when(authClient.getClientById(clientId)).thenReturn(ResponseEntity.ok(client));

        ExternalClientDTO result = authClientWrapper.getClientOrThrow(clientId);
        assertThat(result.isActivo()).isFalse();
    }

    @Test
    @DisplayName("getProviderOrThrow returns provider when found")
    void getProviderOrThrow_Success() {
        UUID providerId = UUID.randomUUID();
        var provider = new com.codefactory.reservasmsreservationservice.dto.external.ExternalProviderDTO();
        provider.setId(providerId);
        provider.setNombreComercial("Test Provider");
        provider.setActivo(true);

        when(authClient.getProviderById(providerId)).thenReturn(ResponseEntity.ok(provider));

        var result = authClientWrapper.getProviderOrThrow(providerId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(providerId);
        assertThat(result.getNombreComercial()).isEqualTo("Test Provider");
    }

    @Test
    @DisplayName("validateClient passes for active client")
    void validateClient_ActiveClient() {
        UUID clientId = UUID.randomUUID();
        ExternalClientDTO client = new ExternalClientDTO();
        client.setId(clientId);
        client.setActivo(true);

        when(authClient.getClientById(clientId)).thenReturn(ResponseEntity.ok(client));

        authClientWrapper.validateClient(clientId);
        verify(authClient).getClientById(clientId);
    }

    @Test
    @DisplayName("validateProvider passes for active provider")
    void validateProvider_ActiveProvider() {
        UUID providerId = UUID.randomUUID();
        var provider = new com.codefactory.reservasmsreservationservice.dto.external.ExternalProviderDTO();
        provider.setId(providerId);
        provider.setActivo(true);

        when(authClient.getProviderById(providerId)).thenReturn(ResponseEntity.ok(provider));

        authClientWrapper.validateProvider(providerId);
        verify(authClient).getProviderById(providerId);
    }

    @Test
    @DisplayName("evictClientCache does not throw")
    void evictClientCache_NoException() {
        UUID clientId = UUID.randomUUID();
        authClientWrapper.evictClientCache(clientId);
    }

    @Test
    @DisplayName("evictProviderCache does not throw")
    void evictProviderCache_NoException() {
        UUID providerId = UUID.randomUUID();
        authClientWrapper.evictProviderCache(providerId);
    }

    @Test
    @DisplayName("userExists returns true when user exists")
    void userExists_True() {
        UUID userId = UUID.randomUUID();
        when(authClient.userExists(userId)).thenReturn(ResponseEntity.ok(true));

        boolean result = authClientWrapper.userExists(userId);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("userExists returns false when user not found")
    void userExists_False() {
        UUID userId = UUID.randomUUID();
        when(authClient.userExists(userId)).thenReturn(ResponseEntity.ok(false));

        boolean result = authClientWrapper.userExists(userId);

        assertThat(result).isFalse();
    }
}