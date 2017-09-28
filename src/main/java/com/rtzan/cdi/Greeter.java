package com.rtzan.cdi;

import com.rtzan.cdi.context.User;

public class Greeter {

    public void greet(User user) {
        System.out.println("Hello mister " + user.getName());
    }

}
