package com.example.repository;

import com.example.model.UserStory;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserStoryRepository {

    private static int internalCounter;

    private static List<UserStory> userStoryList = new ArrayList<>();

    public static UserStory get(int index) {
        return userStoryList.get(index);
    }

    public static int length() {
        return userStoryList.size();
    }

    public static List<UserStory> getAll() {
        return Collections.unmodifiableList(userStoryList);
    }

    public static boolean isEmpty() {
        return userStoryList.isEmpty();
    }

    public static void add(UserStory userStory) {
        userStory.setId(internalCounter++);
        userStoryList.add(userStory);
    }

    public static UserStory replace(int userStoryId, UserStory newStory) {
        for (UserStory us : UserStoryRepository.getAll()) {
            if (us.getId() == userStoryId) {
                userStoryList.remove(us);
                newStory.setId(userStoryId);
                userStoryList.add(newStory);
                return newStory;
            }
        }
        throw new NotFoundException();
    }

    public static boolean containsId(int id){
        for (UserStory us : userStoryList) {
            if (us.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean remove(Object o) {
        return userStoryList.remove(o);
    }

    public static void remove(int index) {
        userStoryList.remove(index);
    }

    public static boolean contains(UserStory userStory) {
        System.out.println(userStoryList.size());
        for(UserStory us : UserStoryRepository.getAll()){

            if(us.equals(userStory))
                return true;
        }
        System.out.println("se");
        return false;
    }

    public static void clear() {
        userStoryList.clear();
    }


}