package com.rtzan.cdi;

import com.rtzan.cdi.context.BoundRequestContextService;

import javax.inject.Inject;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
public class SimpleService {

    @Inject
    private Hello greeter;

    @Inject
    private BoundRequestContextService reqContextSvc;

    public void greet() {
        greeter.greet();
    }


}
