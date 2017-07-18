package com.example.repository;


import com.example.authentication.Token;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TokenRepository {

    private static List<Token> tokens = new ArrayList();

    public static void addToken(Token newToken){
        tokens.add(newToken);
    }

    public static List<Token> getTokens(){
        return Collections.unmodifiableList(tokens);
    }

    public static Token containsValidToken(String username){
        Date currentDate = new Date();
        for(Token token: tokens){
            if(token.getToken().contains(username))
                //issued less then a minute ago -> reuse it
                if(currentDate.getTime() - token.getCreationTime().getTime() < 60000){
                    return token;
                }
        }
        return null;
    }

    public static boolean containsToken(String token){
        for(Token tkn:tokens){
            if(tkn.getToken().equals(token)){
                return true;
            }
        }
        return false;
    }
}
