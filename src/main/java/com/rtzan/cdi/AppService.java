package com.rtzan.cdi;

import com.rtzan.cdi.context.BoundRequestContextService;
import com.rtzan.cdi.context.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

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
        logger.info("Servicing user [{}]", userName);

        Map<String, Object> requestDataStore = new HashMap<>();

        Greeter greeter = new Greeter();

        requestDataStore.put("greeter", greeter);

        reqContextSvc.startRequest(requestDataStore);

        User user = userInstances.get();
        user.setName(userName);

        greetService.greet(greeter);

        reqContextSvc.endRequest(requestDataStore);
    }

}
