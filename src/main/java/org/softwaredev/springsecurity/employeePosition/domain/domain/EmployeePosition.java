package org.softwaredev.springsecurity.employeePosition.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.softwaredev.springsecurity.common.domain.entity.Auditable;
import org.softwaredev.springsecurity.department.domain.entity.Department;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = false,of = {"id"})
@Document("employeePositions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmployeePosition  extends Auditable {

    @Id
    private String id;

    private String name;
    @DBRef(lazy = true)
    private Department department;
}
