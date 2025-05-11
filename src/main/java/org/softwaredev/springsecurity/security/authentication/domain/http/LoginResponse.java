package org.softwaredev.springsecurity.security.authentication.domain.http;

import lombok.Builder;
import org.softwaredev.springsecurity.user.domain.entity.Role;

@Builder
public record LoginResponse(String accessToken, String userRole) {}
