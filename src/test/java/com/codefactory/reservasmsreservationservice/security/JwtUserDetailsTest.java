package com.codefactory.reservasmsreservationservice.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtUserDetails Tests")
class JwtUserDetailsTest {

    @Test
    @DisplayName("JwtUserDetails creation")
    void jwtUserDetails_Created() {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        JwtUserDetails userDetails = new JwtUserDetails("user-123", "user@test.com", "password123", authorities);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("user-123"); // getUsername() returns userId
        assertThat(userDetails.getPassword()).isEqualTo("password123");
    }

    @Test
    @DisplayName("Account status methods")
    void accountStatus_Methods() {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        JwtUserDetails userDetails = new JwtUserDetails("user-123", "user@test.com", "password123", authorities);
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isAccountNonLocked()).isTrue();
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
        assertThat(userDetails.isEnabled()).isTrue();
    }
}