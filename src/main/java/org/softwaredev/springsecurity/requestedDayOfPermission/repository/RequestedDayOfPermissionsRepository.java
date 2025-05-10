package org.softwaredev.springsecurity.requestedDayOfPermission.repository;

import org.softwaredev.springsecurity.requestedDayOfPermission.domain.entity.RequestedDayOfPermission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestedDayOfPermissionsRepository
    extends MongoRepository<RequestedDayOfPermission, String> {}
