package br.com.springsecurity6jwtoauth2.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
