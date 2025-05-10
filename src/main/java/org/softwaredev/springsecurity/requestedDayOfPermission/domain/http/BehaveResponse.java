package org.softwaredev.springsecurity.requestedDayOfPermission.domain.http;

import lombok.Builder;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.PermissionStatus;

@Builder
public record BehaveResponse(PermissionStatus status) {}
