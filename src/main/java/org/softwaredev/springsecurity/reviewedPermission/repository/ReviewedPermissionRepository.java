package org.softwaredev.springsecurity.reviewedPermission.repository;

import org.softwaredev.springsecurity.reviewedPermission.domain.entity.ReviewedPermission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewedPermissionRepository extends MongoRepository<ReviewedPermission, String> {}
