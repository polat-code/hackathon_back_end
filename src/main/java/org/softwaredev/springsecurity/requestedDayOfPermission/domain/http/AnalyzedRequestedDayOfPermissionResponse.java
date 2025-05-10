package org.softwaredev.springsecurity.requestedDayOfPermission.domain.http;

import lombok.Builder;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.ReviewedPermission;

@Builder
public record AnalyzedRequestedDayOfPermissionResponse(
    String id,
    String fullName,
    String position,
    String to,
    String from,
    Integer duration,
    String reason,
    String requestOn,
    ReviewedPermission suggestedPermission) {}
