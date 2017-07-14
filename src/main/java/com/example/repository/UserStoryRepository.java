package com.example.repository;

import com.example.model.UserStory;

import java.util.ArrayList;
import java.util.List;


public class UserStoryRepository {

    private static List<UserStory> userStoryList = new ArrayList<>();

    public static boolean isEmpty() {
        return userStoryList.isEmpty();
    }

    public static boolean add(UserStory userStory) {
        return userStoryList.add(userStory);
    }

    public static boolean remove(Object o) {
        return userStoryList.remove(o);
    }

    public static boolean contains(Object o) {
        return userStoryList.contains(o);
    }

    public static void clear() {
        userStoryList.clear();
    }

}