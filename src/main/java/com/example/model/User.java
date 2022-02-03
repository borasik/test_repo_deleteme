package com.example.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class User implements Serializable {
    public User(){}

    private String firstName;
    private String lastName;

    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String toString(){
        return this.firstName + " " + this.lastName;
    }
}
