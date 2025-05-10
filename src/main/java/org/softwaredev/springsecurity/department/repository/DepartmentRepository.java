package org.softwaredev.springsecurity.department.repository;

import org.softwaredev.springsecurity.department.domain.entity.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {}
