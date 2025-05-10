package org.softwaredev.springsecurity.requestedDayOfPermission.application;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.application.DateTimeUtilService;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.entity.RequestedDayOfPermission;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.http.RequestedDayOfPermissionResponse;
import org.softwaredev.springsecurity.requestedDayOfPermission.repository.RequestedDayOfPermissionsRepository;
import org.softwaredev.springsecurity.reviewedPermission.application.ReviewedPermissionService;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.PermissionStatus;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.ReviewedPermission;
import org.softwaredev.springsecurity.security.authentication.domain.http.RequestedDayOfPermissionRequest;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestedDayOfPermissionService {

  private final RequestedDayOfPermissionsRepository requestedDayOfPermissionsRepository;
  private final DateTimeUtilService dateTimeUtilService;
  private final ReviewedPermissionService reviewedPermissionService;

  public ResponseEntity<ApiResponse<RequestedDayOfPermissionResponse>>
      createRequestedDayOfPermission(
          RequestedDayOfPermissionRequest requestedDayOfPermissionRequest, User user) {
    RequestedDayOfPermission newRequestedDayOfPermission =
        RequestedDayOfPermission.builder()
            .user(user)
            .description(requestedDayOfPermissionRequest.description())
            .to(
                dateTimeUtilService.convertOnlyDateToDateObject(
                    requestedDayOfPermissionRequest.to()))
            .from(
                dateTimeUtilService.convertOnlyDateToDateObject(
                    requestedDayOfPermissionRequest.from()))
            .createdAt(new Date())
            .status(PermissionStatus.PENDING)
            .lastModifiedAt(new Date())
            .build();
    newRequestedDayOfPermission =
        requestedDayOfPermissionsRepository.save(newRequestedDayOfPermission);

    ReviewedPermission reviewedPermission = reviewedPermissionService.findByUser(user);

    reviewedPermission.getRequestedDayOfPermissions().add(newRequestedDayOfPermission);

    reviewedPermission = reviewedPermissionService.save(reviewedPermission);

    return new ResponseEntity<>(
        new ApiResponse<>(
            RequestedDayOfPermissionResponse.builder()
                .id(newRequestedDayOfPermission.getId())
                .description(newRequestedDayOfPermission.getDescription())
                .createdAt(
                    dateTimeUtilService.convertDateToLocalDate(
                        newRequestedDayOfPermission.getCreatedAt(), "Europe/Istanbul"))
                .from(
                    dateTimeUtilService.convertDateToLocalDate(
                        newRequestedDayOfPermission.getFrom(), "Europe/Istanbul"))
                .to(
                    dateTimeUtilService.convertDateToLocalDate(
                        newRequestedDayOfPermission.getTo(), "Europe/Istanbul"))
                .permissionStatus(newRequestedDayOfPermission.getStatus())
                .build(),
            "succes",
            200,
            true,
            new Date()),
        HttpStatus.OK);
  }

  public ResponseEntity<ApiResponse<List<RequestedDayOfPermissionResponse>>>
      getAllRequestedDayOfPerm() {
    List<RequestedDayOfPermission> requestedDayOfPermissions =
        requestedDayOfPermissionsRepository.findAll();
    List<RequestedDayOfPermissionResponse> requestedDayOfPermissionResponses =
        requestedDayOfPermissions.stream()
            .map(
                requestedDayOfPermission ->
                    RequestedDayOfPermissionResponse.builder()
                        .id(requestedDayOfPermission.getId())
                        .createdAt(
                            dateTimeUtilService.convertDateToLocalDate(
                                requestedDayOfPermission.getCreatedAt(), "Europe/Istanbul"))
                        .from(
                            dateTimeUtilService.convertDateToLocalDate(
                                requestedDayOfPermission.getFrom(), "Europe/Istanbul"))
                        .to(
                            dateTimeUtilService.convertDateToLocalDate(
                                requestedDayOfPermission.getTo(), "Europe/Istanbul"))
                        .permissionStatus(requestedDayOfPermission.getStatus())
                        .description(requestedDayOfPermission.getDescription())
                        .build())
            .collect(Collectors.toList());

    return new ResponseEntity<>(
        new ApiResponse<>(requestedDayOfPermissionResponses, "success", 200, true, new Date()),
        HttpStatus.OK);
  }
}
