package org.softwaredev.springsecurity.security.authentication.domain.http;

import org.softwaredev.springsecurity.user.domain.entity.Role;

public record RegisterRequest(
    String email,
    String password,
    String name,
    String surname,
    Role role,
    String employeeStartDate,
    String employeePositionId,
    RequestedDayOfPermissionRequest requestedDayOfPermissionRequest,
    ReviewedPermissionRequest reviewedPermissionRequest) {}
