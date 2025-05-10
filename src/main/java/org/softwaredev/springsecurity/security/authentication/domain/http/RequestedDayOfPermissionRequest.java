package org.softwaredev.springsecurity.security.authentication.domain.http;

public record RequestedDayOfPermissionRequest(
    String createdAt, String from, String to, String description) {}
