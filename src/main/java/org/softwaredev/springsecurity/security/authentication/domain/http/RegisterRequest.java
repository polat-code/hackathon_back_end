package org.softwaredev.springsecurity.security.authentication.domain.http;

public record RegisterRequest(String email, String password, String name, String surname) {}
