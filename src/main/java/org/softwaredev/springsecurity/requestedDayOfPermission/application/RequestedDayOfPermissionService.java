package org.softwaredev.springsecurity.requestedDayOfPermission.application;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.application.DateTimeUtilService;
import org.softwaredev.springsecurity.common.application.MongoUtilService;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.entity.RequestedDayOfPermission;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.http.AnalyzedRequestedDayOfPermissionResponse;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.http.BehaveRequest;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.http.BehaveResponse;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.http.RequestedDayOfPermissionResponse;
import org.softwaredev.springsecurity.requestedDayOfPermission.exceptions.RequestedDayOfPermissionException;
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
  private final MongoUtilService mongoUtilService;

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
                .reason(newRequestedDayOfPermission.getDescription())
                .requestedOn(
                    dateTimeUtilService.convertDateToLocalDate(
                        newRequestedDayOfPermission.getCreatedAt(), "Europe/Istanbul"))
                .from(
                    dateTimeUtilService.convertDateToLocalDate(
                        newRequestedDayOfPermission.getFrom(), "Europe/Istanbul"))
                .to(
                    dateTimeUtilService.convertDateToLocalDate(
                        newRequestedDayOfPermission.getTo(), "Europe/Istanbul"))
                .permissionStatus(newRequestedDayOfPermission.getStatus())
                .duration(
                    getDurationBetweenTwoDates(
                        newRequestedDayOfPermission.getFrom(), newRequestedDayOfPermission.getTo()))
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
    return getRequestedDayOfPermissionsResponseEntity(requestedDayOfPermissions);
  }

  private ResponseEntity<ApiResponse<List<RequestedDayOfPermissionResponse>>>
      getRequestedDayOfPermissionsResponseEntity(
          List<RequestedDayOfPermission> requestedDayOfPermissions) {
    List<RequestedDayOfPermissionResponse> requestedDayOfPermissionResponses =
        requestedDayOfPermissions.stream()
            .map(
                requestedDayOfPermission -> {
                  ReviewedPermission reviewedPermission =
                      reviewedPermissionService.findByUser(requestedDayOfPermission.getUser());
                  RequestedDayOfPermissionResponse requestedDayOfPermissionResponse =
                      RequestedDayOfPermissionResponse.builder()
                          .id(requestedDayOfPermission.getId())
                          .requestedOn(
                              dateTimeUtilService.convertDateToLocalDate(
                                  requestedDayOfPermission.getCreatedAt(), "Europe/Istanbul"))
                          .from(
                              dateTimeUtilService.convertDateToLocalDate(
                                  requestedDayOfPermission.getFrom(), "Europe/Istanbul"))
                          .to(
                              dateTimeUtilService.convertDateToLocalDate(
                                  requestedDayOfPermission.getTo(), "Europe/Istanbul"))
                          .permissionStatus(requestedDayOfPermission.getStatus())
                          .reason(requestedDayOfPermission.getDescription())
                          .duration(
                              getDurationBetweenTwoDates(
                                  requestedDayOfPermission.getFrom(),
                                  requestedDayOfPermission.getTo()))
                          .remainingDay(reviewedPermission.getRemaining())
                          .startedDay(
                              dateTimeUtilService.convertDateToLocalDate(
                                  requestedDayOfPermission.getCreatedAt(), "Europe/Istanbul"))
                          .position(
                              requestedDayOfPermission.getUser().getEmployeePosition() != null ? requestedDayOfPermission.getUser().getEmployeePosition().getName() : "")
                          .department(
                              requestedDayOfPermission
                                  .getUser()
                                  .getEmployeePosition()
                                  .getDepartment()
                                  .getDepartmentName())
                          .userId(requestedDayOfPermission.getUser().getId())
                          .build();

                  return requestedDayOfPermissionResponse;
                })
            .collect(Collectors.toList());

    return new ResponseEntity<>(
        new ApiResponse<>(requestedDayOfPermissionResponses, "success", 200, true, new Date()),
        HttpStatus.OK);
  }

  public Integer getDurationBetweenTwoDates(Date from, Date to) {
    LocalDate start = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate end = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return (int) ChronoUnit.DAYS.between(start, end);
  }

  public static long daysBetween(Date from, Date to) {
    ZoneId zone = ZoneId.systemDefault(); // or pick a specific zone
    LocalDate start = from.toInstant().atZone(zone).toLocalDate();
    LocalDate end = to.toInstant().atZone(zone).toLocalDate();
    return ChronoUnit.DAYS.between(start, end); // exclusive: 1 May → 3 May  ⇒ 2 days
  }

  public static long daysBetweenInclusive(Date from, Date to) {
    return daysBetween(from, to) + 1;
  }

  public ResponseEntity<ApiResponse<BehaveResponse>> behaveRequestedPermission(
      BehaveRequest behaveRequest) {
    RequestedDayOfPermission requestedDayOfPermission = findById(behaveRequest);
    requestedDayOfPermission.setStatus(behaveRequest.status());

    requestedDayOfPermissionsRepository.save(requestedDayOfPermission);

    return new ResponseEntity<>(
        new ApiResponse<>(
            BehaveResponse.builder().status(requestedDayOfPermission.getStatus()).build(),
            "success",
            200,
            true,
            new Date()),
        HttpStatus.OK);
  }

  public RequestedDayOfPermission findById(BehaveRequest behaveRequest) {
    Optional<RequestedDayOfPermission> requestedDayOfPermission =
        requestedDayOfPermissionsRepository.findById(behaveRequest.requestedDayOfPermissionId());
    if (requestedDayOfPermission.isEmpty()) {
      throw new RequestedDayOfPermissionException("requested day of permission not found");
    }
    return requestedDayOfPermission.get();
  }

  public ResponseEntity<ApiResponse<List<RequestedDayOfPermissionResponse>>>
      getAllRequestedDayOfPermByUser(User user) {
    List<RequestedDayOfPermission> requestedDayOfPermissions =
        requestedDayOfPermissionsRepository.findAllByUser(user);

    return getRequestedDayOfPermissionsResponseEntity(requestedDayOfPermissions);
  }

  private class PermissionResult {
    PermissionStatus permissionStatus;
    String description;
  }

  public ResponseEntity<ApiResponse<List<AnalyzedRequestedDayOfPermissionResponse>>>
      getAllAnalyzedRequestedDayOfPermissions() {
    List<RequestedDayOfPermission> requestedDayOfPermissions =
        requestedDayOfPermissionsRepository.findAll();

    /*
       requestedDayOfPermissions.forEach(requestedDayOfPermission -> {
         ReviewedPermission reviewedPermission = reviewedPermissionService.findByUser(requestedDayOfPermission.getUser());

         PermissionResult result = getPermissionResults(reviewedPermission);
         PermissionStatus permissionStatus = PermissionStatus.ALLOWED;
         String message = "";
         Date from = requestedDayOfPermission.getFrom();
         Date to = requestedDayOfPermission.getTo();
         Integer remainingDay = reviewedPermission.getRemaining();
         Long betweenDays = daysBetweenInclusive(from,to);

         if(betweenDays > remainingDay) {
           permissionStatus = PermissionStatus.DENY;
           message = "You don't have enough remaining days";
         }else {
           Long totalWorkDays = daysBetween(requestedDayOfPermission.getUser().getEmployeeStartDate(),requestedDayOfPermission.getCreatedAt());
           if(totalWorkDays <= 180) {
             permissionStatus = PermissionStatus.DENY;
             message = "You can't get any permission without passing 6 months";
           }else {
             // Ask AI to extract whether it is emergency
             permissionStatus  = PermissionStatus.ALLOWED;

           }
         }
       })

       // A. Köprü İzni var mı yok mu kontrol et ?
       // 1. Eğer varsa iznine 1 gün ekle
       // 2. Yoksa devam et.

       // B. Kalan izin < istenen izin ise maximum günce ulaştın de.


       // C. Ilk 6 ayını doldurmamışlara direk red ver.

       // D. İzin projelerin bitime veya başlangıcına denk geldiği zamanları kesinlikle iptal et.

       // E.Performans Değerlendirmesi zamanı ise
       //  Acil Durum İznine (AI Karar verecek)(Max 5 Gün) bakacak
       // 1. Eğer Acil durum varsa istediğinin maximum 5 gün olacak iekilde ver
       // 2. Eğer acil durum değilse reddet

       // F. Department Kotası
       //



    */

    return null;
  }

  private PermissionResult getPermissionResults(ReviewedPermission reviewedPermission) {
    return null;
  }
}
