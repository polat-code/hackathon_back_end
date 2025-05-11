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
    PermissionStatus permissionStatus,
    int remainingDay,
    String startedDay,
    String position,
    String department,
    String userId,
    String fullName) {}


// Kalan izin
// işe başlama tarihi
// pozisyon
// department
// user id
