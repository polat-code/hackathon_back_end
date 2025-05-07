package org.softwaredev.springsecurity.user.userSetting.repository;

import org.softwaredev.springsecurity.user.userSetting.domain.entity.UserSetting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingRepository extends MongoRepository<UserSetting, String> {}
