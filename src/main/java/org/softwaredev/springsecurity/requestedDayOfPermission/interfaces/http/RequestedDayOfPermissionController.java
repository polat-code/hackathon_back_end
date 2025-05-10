package org.softwaredev.springsecurity.requestedDayOfPermission.interfaces.http;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.requestedDayOfPermission.application.RequestedDayOfPermissionService;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.http.RequestedDayOfPermissionResponse;
import org.softwaredev.springsecurity.security.authentication.domain.http.RequestedDayOfPermissionRequest;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/requested-day-of-permission")
public class RequestedDayOfPermissionController {

  private final RequestedDayOfPermissionService requestedDayOfPermissionService;

  @PostMapping("")
  public ResponseEntity<ApiResponse<RequestedDayOfPermissionResponse>> createRequestedDayOfPermission(
          @RequestBody RequestedDayOfPermissionRequest requestedDayOfPermission, HttpServletRequest request) {
      User user = (User) request.getAttribute("user");
      return requestedDayOfPermissionService.createRequestedDayOfPermission(requestedDayOfPermission,user);
  }

  @GetMapping("/all")
  public ResponseEntity<ApiResponse<List<RequestedDayOfPermissionResponse>>> getAllRequestedDayOfPerm() {
      return requestedDayOfPermissionService.getAllRequestedDayOfPerm();
  }

    /*
  @GetMapping("")
    public ResponseEntity<ApiResponse<List<AnalyzedRequestedDayOfPermissionResponse>>> getAllAnalyzedRequestedDayOfPermissions() {
      return requestedDayOfPermissionService.getAllAnalyzedRequestedDayOfPermissions();
  }

     */
}
