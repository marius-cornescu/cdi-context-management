package com.rtzan.cdi.context;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
@RequestScoped
public class User {

    private String name = "user_01";

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
}
