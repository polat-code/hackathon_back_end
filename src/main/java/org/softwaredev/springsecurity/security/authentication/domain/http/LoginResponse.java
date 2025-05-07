package org.softwaredev.springsecurity.security.authentication.domain.http;

import lombok.Builder;

@Builder
public record LoginResponse(String accessToken) {}
