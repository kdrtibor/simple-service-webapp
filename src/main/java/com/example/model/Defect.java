package com.example.model;


public class Defect {

    private int id;
    private String name;
    private int severity;

    public Defect() {
    }

    public Defect(int id, String name, int severity) {
        this.id = id;
        this.name = name;
        this.severity = severity;
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

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }
}
