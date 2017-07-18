package com.example.model;


public class UserStory {

    private int id;
    private String name;
    private int storyPoints;

    public UserStory() {
    }

    public UserStory(int id, String name, int storyPoints) {
        this.id = id;
        this.name = name;
        this.storyPoints = storyPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }

    @Override
    public String toString() {
        return "UserStory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", storyPoints=" + storyPoints +
                '}';
    }
}