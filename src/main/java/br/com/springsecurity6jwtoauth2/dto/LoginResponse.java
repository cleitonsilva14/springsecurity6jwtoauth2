package br.com.springsecurity6jwtoauth2.dto;

public record LoginResponse(org.springframework.security.oauth2.jwt.Jwt accessToken, Long expiresIn) {
}
