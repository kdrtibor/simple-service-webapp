package com.example.rest;

import com.example.authentication.Credentials;
import com.example.authentication.Token;
import com.example.repository.TokenRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Path("/authentication")
public class AuthenticationEndpoint {

    private TokenRepository tokenRepository = new TokenRepository();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {

        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            Token token = issueToken(username);

            // Return the token on the response
            return Response.ok(token.getToken()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        //hardcoded an email
        if(username.equals("abcde"))
            if(password.equals("12345"))
                return;
        else {

                throw new ForbiddenException();
        }
    }

    private Token issueToken(String username) {

        Token token = tokenRepository.containsValidToken(username);

        if(token!=null){
            //try if there are valid tokens
            return token;
        }
        //else branch issue new token
        token  = new Token();
        Date date = new Date();
        token.setToken(UUID.randomUUID() + username + date);
        token.setCreationTime(date);

        tokenRepository.addToken(token);

        return token;
    }
}