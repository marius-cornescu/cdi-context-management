package com.rtzan.cdi;

import com.rtzan.cdi.context.BoundRequestContextService;
import com.rtzan.cdi.context.RequestStore;
import com.rtzan.cdi.context.User;
import com.rtzan.cdi.context.UserRequestScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class AppService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private Instance<User> userInstances;

    @Inject
    private GreetThreadedService greetService;

    @Inject
    private BoundRequestContextService reqContextSvc;


    public void greetMe(String userName) {
        logger.info("1. Servicing user [{}]", userName);

        RequestStore requestDataStore = reqContextSvc.startRequest();

        Greeter greeter = new Greeter();

        User user = userInstances.get();
        user.setName(userName);

        greetService.greet(requestDataStore, greeter);

        reqContextSvc.endRequest(requestDataStore);
    }

    @RequestScoped
    public void greet2(String userName) {
        logger.info("2. Servicing user [{}]", userName);

        Greeter greeter = new Greeter();

        User user = userInstances.get();
        user.setName(userName);

        greetService.greet(null, greeter);

    }

    @UserRequestScope
    public void greetUser(String userName) {
        logger.info("3. Servicing user [{}]", userName);

        Greeter greeter = new Greeter();

        User user = userInstances.get();
        user.setName(userName);

        greetService.greet(null, greeter);
    }

}
