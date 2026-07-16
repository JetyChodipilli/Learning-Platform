package com.learning.platform.dto;

public class AuthResponse {
    private String token;
    private Long id;
    private String role;
    private String email;

    public AuthResponse(String token, Long id, String role, String email) {
        this.token = token;
        this.id = id;
        this.role = role;
        this.email = email;
    }

    public String getToken() { return token; }
    public Long getId() { return id; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
}
