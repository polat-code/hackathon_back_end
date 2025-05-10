package org.softwaredev.springsecurity.requestedDayOfPermission.domain.http;

import lombok.Builder;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.PermissionStatus;

@Builder
public record RequestedDayOfPermissionResponse(
        String id, String createdAt, String from, String to, String description, PermissionStatus permissionStatus) {}
