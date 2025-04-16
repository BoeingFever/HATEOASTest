package com.example.HATEOASTest.userbank;

public class ClientRequest {
    private String username;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClientRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
