package com.example.rest.resource;

import com.example.rest.authentication.Credentials;
import com.example.rest.authentication.Token;
import com.example.repository.TokenRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.UUID;

@Path("/authentication")
public class AuthenticationResource {

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
            return Response.ok(token).build();

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
            System.out.println("found a good token");
            return token;
        }
        System.out.println("havent found a good token");
        //else branch issue new token
        token  = new Token();
        LocalDateTime date =  LocalDateTime.now();
        token.setKey(UUID.randomUUID()+"");
        //here i modified and put "" instead of date
        token.setCreationTime(date);
        token.setUserName(username);

        tokenRepository.addToken(token);

        return token;
    }
}