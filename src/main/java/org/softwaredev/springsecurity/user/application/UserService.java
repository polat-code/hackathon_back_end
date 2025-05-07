package org.softwaredev.springsecurity.user.application;

import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.security.authentication.exceptions.UserNotFoundException;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.softwaredev.springsecurity.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User findByEmail(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("User is not found for email : " + email);
    }
    return optionalUser.get();
  }
}
