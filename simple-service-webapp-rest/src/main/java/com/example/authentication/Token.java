package com.example.authentication;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
public class Token {

    private String token;
    private Date creationTime;

    public Token() {
    }

    public Token(String token, Date creationTime) {
        this.token = token;
        this.creationTime = creationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
