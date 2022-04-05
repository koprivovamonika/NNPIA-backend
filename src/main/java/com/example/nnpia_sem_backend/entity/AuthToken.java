package com.example.nnpia_sem_backend.entity;

public class AuthToken {

    private String token;
    private String username;
    private Long id;

    public AuthToken(){

    }

    public AuthToken(String token, String username, Long id){
        this.token = token;
        this.username = username;
        this.id = id;
    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
