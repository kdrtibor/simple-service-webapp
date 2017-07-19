package com.example.rest.resource;

import com.example.rest.authentication.Secured;
import com.example.model.UserStory;
import com.example.repository.UserStoryRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/userstories")
public class UserStoryResource {


    @GET
    @Secured
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
        throw new NotFoundException();
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserStory postUserStory(UserStory story){

        UserStoryRepository.add(story);
        return story;
    }

    @PUT
    @Secured
    @Path("/{userStoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserStory updateUserStory(@PathParam("userStoryId") int userStoryId, UserStory newStory) {
        for (UserStory us : UserStoryRepository.getAll()) {
            if (us.getId() == userStoryId) {
                UserStoryRepository.remove(us);
                UserStoryRepository.add(newStory);
                return newStory;
            }
        }
        throw new NotFoundException();
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
        throw new NotFoundException();
    }
}