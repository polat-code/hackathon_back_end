package org.softwaredev.springsecurity.user.domain.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.softwaredev.springsecurity.common.domain.entity.Auditable;
import org.softwaredev.springsecurity.employeePosition.domain.domain.EmployeePosition;
import org.softwaredev.springsecurity.user.userSetting.domain.entity.UserSetting;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document("_users")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User extends Auditable implements UserDetails {

  @Id private String id;

  @NotBlank private String name;
  @NotBlank private String surname;

  @NotBlank @Email private String email;
  @NotBlank private String password;

  @NotNull private List<Permission> permissions;

  private Date employeeStartDate;

  @NotNull private Role role;

  @DBRef(lazy = true)
  @NotNull
  private EmployeePosition employeePosition;

  @DBRef(lazy = true)
  private UserSetting userSetting;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities =
        permissions.stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
