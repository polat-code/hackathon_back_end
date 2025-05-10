package org.softwaredev.springsecurity.employeePosition.repository;

import org.softwaredev.springsecurity.employeePosition.domain.domain.EmployeePosition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePositionRepository extends MongoRepository<EmployeePosition, String> {}
