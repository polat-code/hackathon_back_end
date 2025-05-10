package org.softwaredev.springsecurity.reviewedPermission.domain.entity;

import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.softwaredev.springsecurity.common.domain.entity.Auditable;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.entity.RequestedDayOfPermission;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("reviewedPermissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(
    callSuper = false,
    of = {"id"})
@ToString(
    callSuper = false,
    of = {"id"})
public class ReviewedPermission extends Auditable {

  @Id private String id;

  @DBRef(lazy = true)
  private User user;

  @DBRef(lazy = true)
  private List<RequestedDayOfPermission> requestedDayOfPermissions;

  private PermissionStatus status;

  private Integer usage;
  private Integer remaining;
}
