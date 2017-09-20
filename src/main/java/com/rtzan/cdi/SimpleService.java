package com.rtzan.cdi;

import javax.inject.Inject;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
public class SimpleService {
    
    @Inject
    private Hello greeter;

    public void greet() {
        greeter.greet();
    }
}
