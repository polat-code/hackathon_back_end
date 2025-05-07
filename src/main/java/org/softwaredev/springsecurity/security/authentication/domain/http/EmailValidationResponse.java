package org.softwaredev.springsecurity.security.authentication.domain.http;

import lombok.Builder;

@Builder
public record EmailValidationResponse(boolean validated) {}
