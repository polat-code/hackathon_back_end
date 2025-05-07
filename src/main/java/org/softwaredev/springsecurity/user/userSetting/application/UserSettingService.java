package org.softwaredev.springsecurity.user.userSetting.application;

import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.user.userSetting.domain.entity.UserSetting;
import org.softwaredev.springsecurity.user.userSetting.repository.UserSettingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingService {
  private final UserSettingRepository userSettingRepository;

  public UserSetting saveUserSetting(UserSetting userSetting) {
    return userSettingRepository.save(userSetting);
  }
}
