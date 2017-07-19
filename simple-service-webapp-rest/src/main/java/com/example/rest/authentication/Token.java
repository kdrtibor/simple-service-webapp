package com.example.rest.authentication;

import java.time.LocalDateTime;

public class Token {

    private String key;
    private String userName;
    private LocalDateTime creationTime;

    public Token() {
    }

    public Token(String key, String userName, LocalDateTime creationTime) {
        this.key = key;
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

//    public void setCreationTime(String creationTime) {
//        int year, month, day, hour, minute, second;
//        String[] date = creationTime.split(" ");
//        this.creationTime = LocalDateTime.now();
//    }


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
