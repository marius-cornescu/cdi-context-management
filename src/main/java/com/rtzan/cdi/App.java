package com.rtzan.cdi;

import javax.enterprise.event.Observes;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
public class App {
    public void onEvent(@Observes SimpleEvent ignored, SimpleService service) {
        service.greet();
    }
}
