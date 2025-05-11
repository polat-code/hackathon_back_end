package org.softwaredev.springsecurity.security.authentication.domain.http;

import lombok.Builder;

@Builder
public record UserDetailResponse(String fullName, String role) {}
