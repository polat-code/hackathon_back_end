package org.softwaredev.springsecurity.requestedDayOfPermission.domain.http;

import lombok.Builder;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.PermissionStatus;

@Builder
public record RequestedDayOfPermissionResponse(
    String id,
    String from,
    String to,
    Integer duration,
    String reason,
    String requestedOn,
    PermissionStatus permissionStatus) {}
