package com.rtzan.cdi.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Parameter;

@UserRequestScope
@Interceptor
public class UserRequestScopeInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private BoundRequestContextService reqContextSvc;

    @AroundInvoke
    public Object userContextControl(InvocationContext ctx) throws Exception {
        Parameter[] parameters = ctx.getMethod().getParameters();

        if (parameters == null) {
            return ctx.proceed();
        }

        Object returnValue = null;

        returnValue = ctx.proceed();

        return returnValue;
    }
}
