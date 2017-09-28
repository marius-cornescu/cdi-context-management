package com.rtzan.cdi;

import com.rtzan.cdi.context.BoundRequestContextService;
import com.rtzan.cdi.context.User;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
public class OtherService {

    @Inject
    private OtherService2 otherService;

    public void greet(Greeter greeter) {
        otherService.greet(greeter);
    }

}
