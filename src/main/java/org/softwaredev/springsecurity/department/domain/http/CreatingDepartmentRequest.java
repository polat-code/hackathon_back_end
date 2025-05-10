package org.softwaredev.springsecurity.department.domain.http;

import lombok.Builder;

@Builder
public record CreatingDepartmentRequest(String id, String departmentName) {}
