package org.softwaredev.springsecurity.department.domain.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.softwaredev.springsecurity.common.domain.entity.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(
    callSuper = false,
    of = {"id"})
@ToString(
    callSuper = false,
    of = {"id"})
public class Department extends Auditable {

  @Id private String id;
  private String departmentName;
}
