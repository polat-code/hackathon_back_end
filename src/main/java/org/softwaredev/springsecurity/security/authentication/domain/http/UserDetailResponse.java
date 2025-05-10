package org.softwaredev.springsecurity.security.authentication.domain.http;

import lombok.Builder;
import org.softwaredev.springsecurity.user.domain.entity.Role;

@Builder
public record UserDetailResponse(String name, String surname, Role role) {}
