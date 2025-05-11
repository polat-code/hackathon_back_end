package org.softwaredev.springsecurity.user.interfaces.http;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.security.authentication.application.AuthenticationService;
import org.softwaredev.springsecurity.security.authentication.domain.http.UserDetailResponse;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/api/v1/user")
public class UserController {

  private final AuthenticationService authenticationService;

  /*
  public ResponseEntity<ApiResponse<List<UserCreationResponse>>> createNewUser(
          @RequestBody List<UserCreationRequest>  userCreationRequest, HttpServletRequest request) {

      return userService.createNewUser(userCreationRequest,);
  }

   */

  @GetMapping("/detail")
  public ResponseEntity<ApiResponse<UserDetailResponse>> getUserDetailResponse(
      HttpServletRequest request) {
    User user = (User) request.getAttribute("user");
    return authenticationService.getUserDetailResponse(user);
  }
}
