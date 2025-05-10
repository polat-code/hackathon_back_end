package org.softwaredev.springsecurity.employeePosition.application;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.department.application.DepartmentService;
import org.softwaredev.springsecurity.department.domain.entity.Department;
import org.softwaredev.springsecurity.employeePosition.domain.domain.EmployeePosition;
import org.softwaredev.springsecurity.employeePosition.domain.http.CreatingEmployeePositionRequest;
import org.softwaredev.springsecurity.employeePosition.repository.EmployeePositionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeePositionService {
    
    private final EmployeePositionRepository employeePositionRepository;
  private final DepartmentService departmentService;

  public ResponseEntity<ApiResponse<List<EmployeePosition>>> createEmployeePositions(
      List<CreatingEmployeePositionRequest> creatingEmployeePositions) {

    List<EmployeePosition> employeePositions =
        creatingEmployeePositions.stream()
            .map(
                creatingEmployeePositionRequest -> {
                  Department department =
                      departmentService.findById(creatingEmployeePositionRequest.departmentId());
                  return EmployeePosition.builder()
                      .id(creatingEmployeePositionRequest.id())
                      .name(creatingEmployeePositionRequest.name())
                      .department(department)
                      .createdAt(new Date())
                      .lastModifiedAt(new Date())
                      .build();
                })
            .collect(Collectors.toList());
    employeePositionRepository.saveAll(employeePositions);
    return new ResponseEntity<>(
        new ApiResponse<>(employeePositions, "success", 200, true, new Date()), HttpStatus.OK);
  }
}
