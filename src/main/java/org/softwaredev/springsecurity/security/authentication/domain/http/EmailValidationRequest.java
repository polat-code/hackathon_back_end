package org.softwaredev.springsecurity.security.authentication.domain.http;

import jakarta.validation.constraints.NotBlank;

public record EmailValidationRequest(@NotBlank String email,@NotBlank String otp) {}
