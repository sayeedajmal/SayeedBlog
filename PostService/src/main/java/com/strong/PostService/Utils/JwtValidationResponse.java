package com.strong.PostService.Utils;

import java.util.List;

public class JwtValidationResponse {
    private String username;
    private List<String> roles;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
