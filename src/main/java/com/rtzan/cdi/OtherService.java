package com.rtzan.cdi;

import com.rtzan.cdi.context.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
@ApplicationScoped
public class OtherService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private User user;

    public void greet(Greeter greeter) {
        logger.debug("Will trigger greet on [{}] for [{}]", greeter, user);

        greeter.greet(user);
    }

}
