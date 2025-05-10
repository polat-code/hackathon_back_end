package org.softwaredev.springsecurity.requestedDayOfPermission.application;

import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.requestedDayOfPermission.repository.RequestedDayOfPermissionsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestedDayOfPermissionService {

  private final RequestedDayOfPermissionsRepository requestedDayOfPermissionsRepository;
}
