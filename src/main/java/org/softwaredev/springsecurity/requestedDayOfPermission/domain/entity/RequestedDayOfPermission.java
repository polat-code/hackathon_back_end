package org.softwaredev.springsecurity.requestedDayOfPermission.domain.entity;

import java.util.Date;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.softwaredev.springsecurity.common.domain.entity.Auditable;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.PermissionStatus;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("requestedDayOfPermissions")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(
    callSuper = false,
    of = {"id"})
@ToString(
    callSuper = false,
    of = {"id"})
public class RequestedDayOfPermission extends Auditable {
  @Id private String id;

  private Date from;
  private Date to;

  @DBRef(lazy = true)
  private User user;

  private String description;

  private PermissionStatus status;
}
