package com.rtzan.cdi;

import com.rtzan.cdi.context.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
@ApplicationScoped
public class GreetThreadedService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExecutorService executor;

    @Inject
    private OtherService otherService;

    @PostConstruct
    public void init() {
        executor = Executors.newCachedThreadPool();
    }

    public void greet(Greeter greeter) {
        logger.debug("Processing greeter [{}]", greeter);

        executor.submit(() -> otherService.greet(greeter));
    }

}
