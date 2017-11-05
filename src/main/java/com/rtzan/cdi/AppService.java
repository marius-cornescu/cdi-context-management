package com.rtzan.cdi;

import com.rtzan.cdi.context.BoundRequestContextService;
import com.rtzan.cdi.context.RequestStore;
import com.rtzan.cdi.context.User;
import com.rtzan.cdi.context.UserRequestScope;
import javax.enterprise.context.ContextNotActiveException;
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

        User user = userInstances.get();
        user.setName(userName);

        greetService.greet(requestDataStore);

        reqContextSvc.endRequest(requestDataStore);
    }

    @RequestScoped
    public void greet2(String userName) {
        logger.info("2. Servicing user [{}]", userName);

        User user = userInstances.get();
        try {
            user.toString();
        } catch (ContextNotActiveException e) {
            // WELD-001303: No active contexts for scope type javax.enterprise.context.RequestScoped
            // if called from a thread
            logger.warn(e.getMessage());
            logger.trace("", e);
        }
        user.setName(userName);

        greetService.greet();
    }

    @UserRequestScope // custom annotation used with interceptor
    public void greetUser(String userName) {
        logger.info("3. Servicing user [{}]", userName);

        User user = userInstances.get();
        user.setName(userName);

        // TODO: have the requestStore injected by interceptor
        greetService.greet(null);
    }

}
