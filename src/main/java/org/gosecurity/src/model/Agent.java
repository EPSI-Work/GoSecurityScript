package org.gosecurity.src.model;

import java.util.List;

public class Agent {
    private String lastname;
    private String firstname;
    private String mission;
    private String password;
    private List<Tool> tools;

    public Agent(String lastname, String firstname, String mission, String password, List<Tool> tools){
        this.lastname = lastname;
        this.firstname = firstname;
        this.mission = mission;
        this.password = password;
        this.tools = tools;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }
}
