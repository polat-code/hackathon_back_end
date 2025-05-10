package org.softwaredev.springsecurity.reviewedPermission.repository;

import org.softwaredev.springsecurity.reviewedPermission.domain.entity.ReviewedPermission;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewedPermissionRepository extends MongoRepository<ReviewedPermission, String> {
    Optional<ReviewedPermission> findByUser(User user);
}
