package org.softwaredev.springsecurity.requestedDayOfPermission.domain.http;

import lombok.Builder;

@Builder
public record RequestedDayOfPermissionResponse(
    String id, String createdAt, String from, String to, String description) {}
