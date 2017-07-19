package com.example.repository;


import com.example.rest.authentication.Token;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenRepository {

    private static final int MINUTE = 60000;
    private static List<Token> tokens = new ArrayList();

    public static void addToken(Token newToken){
        tokens.add(newToken);
    }

    public static List<Token> getTokens(){
        return Collections.unmodifiableList(tokens);
    }

    public static Token containsValidToken(String username){
        System.out.println("check if there is a token");
        LocalDateTime currentDate = LocalDateTime.now();
        for(Token token: tokens){
            if(token.getUserName().equals(username))
                //issued less then a minute ago -> reuse it
                if(ChronoUnit.MINUTES.between(currentDate,token.getCreationTime()) == 0){
                    System.out.println("there is a token");
                    return token;
                }
        }
        System.out.println("there is no token");
        return null;
    }

    public static boolean containsToken(String token){
        for(Token tkn:tokens){
            if(tkn.getKey().equals(token)){
                return true;
            }
        }
        return false;
    }

    public static LocalDateTime getTokenCreationTime(String key){
        for(Token token: tokens){
            if(token.getKey().equals(key)){
                return token.getCreationTime();
            }
        }
        return null;
    }
}
