package org.softwaredev.springsecurity.security.authentication.interfaces.http;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.security.authentication.application.AuthenticationService;
import org.softwaredev.springsecurity.security.authentication.domain.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
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


}
