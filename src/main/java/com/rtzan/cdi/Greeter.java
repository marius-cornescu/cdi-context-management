package com.rtzan.cdi;

import com.rtzan.cdi.context.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Greeter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void greet(User user) {
        logger.info("Hello mister " + user.getName());
    }

}
