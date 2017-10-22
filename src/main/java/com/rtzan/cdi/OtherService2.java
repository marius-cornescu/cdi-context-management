package com.rtzan.cdi;

import com.rtzan.cdi.context.User;

import javax.enterprise.context.spi.Context;
import javax.inject.Inject;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
public class OtherService2 {

    @Inject
    private User user;

//    @Inject
//    private Context context;

    public void greet(Greeter greeter) {
        greeter.greet(user);
    }

}
