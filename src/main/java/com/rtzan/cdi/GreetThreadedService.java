package com.rtzan.cdi;

import com.rtzan.cdi.context.BoundRequestContextService;
import com.rtzan.cdi.context.RequestStore;
import com.rtzan.cdi.context.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
@ApplicationScoped
public class GreetThreadedService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExecutorService executor;

    @Inject
    private OtherService otherService;

    @Inject
    private User user;

    @Inject
    private BoundRequestContextService reqContextSvc;

    @PostConstruct
    public void init() {
        executor = Executors.newCachedThreadPool();
    }

    public void greet(RequestStore requestDataStore, Greeter greeter) {
        logger.debug("Processing greeter [{}] and [{}]", greeter, user);

        Future<?> future = executor.submit(() -> {
            reqContextSvc.resumeRequest(requestDataStore);
            otherService.greet(greeter, user);
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("", e);
        }
    }

}
