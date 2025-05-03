package org.softwaredev.springsecurity.common.domain.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Auditable implements Serializable {

  @PastOrPresent @NotNull private Date createdAt;

  @PastOrPresent @NotNull private Date lastModifiedAt;
}
