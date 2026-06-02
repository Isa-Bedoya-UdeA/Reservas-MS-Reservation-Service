package com.codefactory.reservasmsreservationservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AsyncConfig Tests")
class AsyncConfigTest {

    @Test
    @DisplayName("EmailExecutor bean creation")
    void emailExecutor_Created() {
        AsyncConfig config = new AsyncConfig();
        Executor executor = config.emailExecutor();
        assertThat(executor).isNotNull();
    }

    @Test
    @DisplayName("TaskExecutor bean creation")
    void taskExecutor_Created() {
        AsyncConfig config = new AsyncConfig();
        Executor executor = config.taskExecutor();
        assertThat(executor).isNotNull();
    }
}