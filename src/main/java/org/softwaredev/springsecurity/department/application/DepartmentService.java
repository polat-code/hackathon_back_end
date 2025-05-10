package org.softwaredev.springsecurity.department.application;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.department.domain.entity.Department;
import org.softwaredev.springsecurity.department.domain.http.CreatingDepartmentRequest;
import org.softwaredev.springsecurity.department.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
  private final DepartmentRepository departmentRepository;

  public ResponseEntity<ApiResponse<List<Department>>> createDepartment(
      List<CreatingDepartmentRequest> creatingDepartmentRequests) {

    List<Department> departments =
        creatingDepartmentRequests.stream()
            .map(
                creatingDepartmentRequest ->
                    Department.builder()
                        .id(creatingDepartmentRequest.id())
                        .departmentName(creatingDepartmentRequest.departmentName())
                        .createdAt(new Date())
                        .lastModifiedAt(new Date())
                        .build())
            .collect(Collectors.toList());
    departmentRepository.saveAll(departments);

    return new ResponseEntity<>(
        new ApiResponse<>(departments, "success", 200, true, new Date()), HttpStatus.OK);
  }
}
