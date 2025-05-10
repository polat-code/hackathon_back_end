package org.softwaredev.springsecurity.employeePosition.interfaces.http;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.employeePosition.application.EmployeePositionService;
import org.softwaredev.springsecurity.employeePosition.domain.domain.EmployeePosition;
import org.softwaredev.springsecurity.employeePosition.domain.http.CreatingEmployeePositionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee-position")
@RequiredArgsConstructor
public class EmployeePositionsController {

  private final EmployeePositionService employeePositionService;

  @PostMapping("")
  public ResponseEntity<ApiResponse<List<EmployeePosition>>> createEmployeePositions(
      @RequestBody List<CreatingEmployeePositionRequest> creatingEmployeePositions) {
    return employeePositionService.createEmployeePositions(creatingEmployeePositions);
  }
}
