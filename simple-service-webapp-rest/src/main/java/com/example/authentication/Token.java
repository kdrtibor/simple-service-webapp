package com.example.authentication;

import java.time.LocalDateTime;
import java.util.Date;

public class Token {

    private String key;
    private String userName;
    private LocalDateTime creationTime;

    public Token() {
    }

    public Token(String token, String userName, LocalDateTime creationTime) {
        this.key = token;
        this.userName = userName;
        this.creationTime = creationTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + key + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
