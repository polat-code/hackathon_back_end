package org.softwaredev.springsecurity.user.application;

import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;


}
