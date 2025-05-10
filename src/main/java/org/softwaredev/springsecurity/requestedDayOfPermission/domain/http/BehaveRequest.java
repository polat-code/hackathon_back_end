package org.softwaredev.springsecurity.requestedDayOfPermission.domain.http;

import org.softwaredev.springsecurity.reviewedPermission.domain.entity.PermissionStatus;

public record BehaveRequest(PermissionStatus status, String requestedDayOfPermissionId) {}
