package com.example.rest;

import com.example.model.UserStory;
import com.example.repository.DefectRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/defects")
public class DefectResource {

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test() {
        return "Test  def ---";
    }


}