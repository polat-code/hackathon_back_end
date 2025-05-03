package org.softwaredev.springsecurity.common.domain.entity;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    NOT_APPROVED(410),
    NOT_VALID(411),
    NOT_FOUND(404),
    EXPIRED(498),
    NOT_ACCEPTABLE(406),
    ALREADY_EXIST(409),
    BAD_REQUEST(400),
    PERMISSION_BASED_DENIAL(451),
    ROLE_BASED_DENIAL(452),
    INTERNAL_SERVER_ERROR(500),
    UNPROCESSABLE_ENTITY(422),
    UNAUTHORIZED(401),
    FORBIDDEN(403);

    private final int errorCode;

    ErrorCodes(int errorCode) {
        this.errorCode = errorCode;
    }
}
