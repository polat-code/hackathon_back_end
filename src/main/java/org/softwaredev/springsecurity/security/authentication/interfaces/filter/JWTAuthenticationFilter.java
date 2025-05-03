package org.softwaredev.springsecurity.security.authentication.interfaces.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.softwaredev.springsecurity.common.domain.entity.ErrorCodes;
import org.softwaredev.springsecurity.security.authentication.application.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final JWTService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwtToken;
    final String userEmail;
    try {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }
      jwtToken = authHeader.substring(7);
      userEmail = jwtService.extractUsernameFromAccessToken(jwtToken);
      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        if (jwtService.isAccessTokenValid(jwtToken, userDetails)) {
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);

          // To fetch user in the controller
          request.setAttribute("user", userDetails);
        }
      }
      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException ex) {
      handleExpiredJwtException(response, ex);
    } catch (JwtException ex) {
      System.out.println(ex.getMessage());
      handleJwtException(response, ex);
    }
  }

  private void handleJwtException(@NonNull HttpServletResponse response, Exception ex)
      throws IOException {
    JSONObject jsonResponse =
        createJsonObject(ex.getMessage(), ErrorCodes.UNAUTHORIZED.getErrorCode());
    setResponseValues(response, jsonResponse);
  }

  private void setResponseValues(@NonNull HttpServletResponse response, JSONObject jsonResponse)
      throws IOException {
    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(jsonResponse.toString());
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,PATCH");
    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
    response.setHeader("Access-Control-Allow-Credentials", "true");
  }

  private void handleExpiredJwtException(
      @NonNull HttpServletResponse response, ExpiredJwtException ex) throws IOException {
    JSONObject jsonResponse =
        createJsonObject("JWT token has expired", ErrorCodes.EXPIRED.getErrorCode());
    setResponseValues(response, jsonResponse);
  }

  private JSONObject createJsonObject(String message, int errorCode) {
    JSONObject jsonResponse = new JSONObject();
    jsonResponse.put("message", "Error");
    jsonResponse.put("details", message);
    jsonResponse.put("statusCode", errorCode);
    jsonResponse.put("success", false);
    jsonResponse.put("timestamp", new Date().getTime());
    return jsonResponse;
  }
}
