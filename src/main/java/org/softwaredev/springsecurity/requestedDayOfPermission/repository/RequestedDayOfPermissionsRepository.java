package org.softwaredev.springsecurity.requestedDayOfPermission.repository;

import org.softwaredev.springsecurity.requestedDayOfPermission.domain.entity.RequestedDayOfPermission;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestedDayOfPermissionsRepository
    extends MongoRepository<RequestedDayOfPermission, String> {
    List<RequestedDayOfPermission> findAllByUser(User user);
}
