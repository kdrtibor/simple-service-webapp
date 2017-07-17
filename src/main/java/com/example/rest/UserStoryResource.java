package com.example.rest;

import com.example.model.UserStory;
import com.example.repository.UserStoryRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/userstories")
public class UserStoryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserStory> getAll() {
        return UserStoryRepository.getAll();
    }

    @GET
    @Path("/{userStoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserStory getUserStory(@PathParam("userStoryId") int userStoryId){
        for(UserStory us : UserStoryRepository.getAll()){
            if(us.getId() == userStoryId){
                return us;
            }
        }
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postUserStory(UserStory story){
        UserStoryRepository.add(story);
        return "successfully posted";
    }

    @PUT
    @Path("/{userStoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUserStory(@PathParam("userStoryId") int userStoryId,UserStory newStory) {
        for (UserStory us : UserStoryRepository.getAll()) {
            if (us.getId() == userStoryId) {
                UserStoryRepository.remove(us);
                UserStoryRepository.add(newStory);
                return "Updated successfully";
            }
        }
        return "Object to update not found";
    }


    @DELETE
    @Path("/{userStoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUserStory(@PathParam("userStoryId") int userStoryId){
        for (UserStory us : UserStoryRepository.getAll()) {
            if (us.getId() == userStoryId) {
                UserStoryRepository.remove(us);
                return "Deleted successfully";
            }
        }
        return "Object to delete not found";
    }
}