package org.softwaredev.springsecurity.department.interfaces.http;

import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.department.application.DepartmentService;
import org.softwaredev.springsecurity.department.domain.entity.Department;
import org.softwaredev.springsecurity.department.domain.http.CreatingDepartmentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<List<Department>>> createDepartment(@RequestBody List<CreatingDepartmentRequest> creatingDepartmentRequest) {
        return departmentService.createDepartment(creatingDepartmentRequest);
    }
}

