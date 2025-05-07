package org.softwaredev.springsecurity.security.authentication.domain.http;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String email, @NotBlank String password) {}
