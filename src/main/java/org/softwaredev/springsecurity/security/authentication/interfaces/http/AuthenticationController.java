package org.softwaredev.springsecurity.security.authentication.interfaces.http;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.security.authentication.application.AuthenticationService;
import org.softwaredev.springsecurity.security.authentication.domain.http.*;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }
    @PostMapping("/register-multiple")
    public ResponseEntity<ApiResponse<List<RegisterResponse>>> multipleRegister(@RequestBody List<RegisterRequest> registerRequest) {
        return authenticationService.multipleRegister(registerRequest);
    }

  @PostMapping("/email-validation")
  public ResponseEntity<ApiResponse<EmailValidationResponse>> validateOTP(
      @RequestBody @Valid EmailValidationRequest emailValidationRequest) {
    return authenticationService.validateEmail(emailValidationRequest);
  }

  @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
  }

    @GetMapping("/validate-access-token")
    public ResponseEntity<ApiResponse<String>> validateAccessToken(@RequestHeader HttpHeaders httpHeaders){
        return authenticationService.validateAccessToken(httpHeaders);
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserDetailResponse(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return authenticationService.getUserDetailResponse(user);
    }

}
