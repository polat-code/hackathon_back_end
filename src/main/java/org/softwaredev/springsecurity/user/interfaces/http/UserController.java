package org.softwaredev.springsecurity.user.interfaces.http;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.user.application.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;

  /*
  public ResponseEntity<ApiResponse<List<UserCreationResponse>>> createNewUser(
          @RequestBody List<UserCreationRequest>  userCreationRequest, HttpServletRequest request) {

      return userService.createNewUser(userCreationRequest,);
  }

   */
}
