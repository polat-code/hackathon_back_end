package org.softwaredev.springsecurity.employeePosition.application;

import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.employeePosition.repository.EmployeePositionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeePositionService {
    
    private final EmployeePositionRepository employeePositionRepository;
}
