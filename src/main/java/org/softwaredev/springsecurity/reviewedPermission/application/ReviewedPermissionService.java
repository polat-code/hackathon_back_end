package org.softwaredev.springsecurity.reviewedPermission.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.reviewedPermission.exceptions.ReviewedPermissionNotFoundException;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.ReviewedPermission;
import org.softwaredev.springsecurity.reviewedPermission.repository.ReviewedPermissionRepository;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewedPermissionService {

  private final ReviewedPermissionRepository reviewedPermissionRepository;

  public ReviewedPermission findByUser(User user) {
    Optional<ReviewedPermission> reviewedPermission = reviewedPermissionRepository.findByUser(user);
    if (reviewedPermission.isEmpty()) {
      throw new ReviewedPermissionNotFoundException(
          "That user doesn't exist reviewedPermission object");
    }
    return reviewedPermission.get();
  }

  public ReviewedPermission save(ReviewedPermission reviewedPermission) {
    return reviewedPermissionRepository.save(reviewedPermission);
  }
}
