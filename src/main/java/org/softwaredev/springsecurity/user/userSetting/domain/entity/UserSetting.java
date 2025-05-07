package org.softwaredev.springsecurity.user.userSetting.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("userSettings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class UserSetting {

  @Id private String id;
  private String otp;
  private Date otpCreatedTime;

  private boolean emailVerified;
}
