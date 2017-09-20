package com.rtzan.cdi.context;

import org.jboss.weld.context.bound.BoundRequestContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
@ApplicationScoped
public class BoundRequestContextService {

    @Inject
    private BoundRequestContext requestContext;




    /**
     * Start the request, providing a data store which will last the lifetime of the request
     */
    public void startRequest(Map<String, Object> requestDataStore) {
        // Associate the store with the context and activate the context
        requestContext.associate(requestDataStore);

        requestContext.activate();
    }


    /**
     * End the request, providing the same data store as was used to start the request
     */
    public void endRequest(Map<String, Object> requestDataStore) {
        try {

            // Invalidate the request (all bean instances will be scheduled for destruction) */
            requestContext.invalidate();

            // Deactivate the request, causing all bean instances to be destroyed (as the context is invalid) */
            requestContext.deactivate();

        } finally {
            // Ensure that whatever happens we dissociate to prevent any memory leaks */
            requestContext.dissociate(requestDataStore);
        }

    }

}