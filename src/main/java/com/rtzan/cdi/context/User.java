package com.rtzan.cdi.context;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
@RequestScoped
public class User {

    private String name;

    @PostConstruct
    public void afterCreate() {
        System.out.println("user created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
