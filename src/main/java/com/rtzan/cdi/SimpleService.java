package com.rtzan.cdi;

import com.rtzan.cdi.context.BoundRequestContextService;
import com.rtzan.cdi.context.User;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SimpleService {

    @Inject
    private Instance<User> userInstances;

    @Inject
    private OtherService otherService;

    @Inject
    private BoundRequestContextService reqContextSvc;

    public void greetMe(String userName) {
        Map<String, Object> requestDataStore = new HashMap<>();

        Greeter greeter = new Greeter();

        requestDataStore.put("greeter", greeter);

        reqContextSvc.startRequest(requestDataStore);

        User user = userInstances.get();
        user.setName(userName);

        otherService.greet(greeter);

        reqContextSvc.endRequest(requestDataStore);
    }

}
