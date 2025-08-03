package com.pefonseca.auth.domain.user;

public record AuthenticationDTO(
        String login,
        String password
) {
}
