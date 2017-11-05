package com.rtzan.cdi;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
@RunWith(Arquillian.class)
public class CustomCDIContextTest {

    private long wait_seconds = 5;

    private ExecutorService executor;

    @Inject
    private AppService appService;

    @Deployment
    public static JavaArchive deployment() {
        JavaArchive archive =
                ShrinkWrap.create(JavaArchive.class)
                        // Test classes
                        .addPackages(true, AppService.class.getPackage())
                        // Bean archive deployment descriptor
                        .addAsManifestResource("META-INF/beans-test.xml", "beans.xml");
        ;

        return archive;
    }

    @Before
    public void setUp() throws Exception {
        executor = Executors.newCachedThreadPool();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Calling the service from within the same thread (MAIN thread),
     * so the request storage was created for the RequestContext (in org.jboss.arquillian.container.weld.embedded.LifecycleHandler.createRequest())
     * => while in the thread, we have context access, but as we move to another thread, we lose the context => NPE on the context created bean
     */
    @Test
    public void testOneCallWithRequestScope() throws Exception {
        appService.greet2("Marius");
    }

    /**
     * Call from a thread, the method that creates its own ReqContext + storage, so we can still create & inject context bound beans
     */
    @Test
    public void testParallelCall() throws Exception {
        triggerAsync(s -> appService.greetMe(s), "Marius", "Monica", "Test");

        Thread.sleep(wait_seconds * 1000L);
    }

    @Test
    public void testParallelWithRequestScope() throws Exception {
        triggerAsync(s -> appService.greet2(s), "Marius", "Monica", "Test");

        Thread.sleep(wait_seconds * 1000L);
    }

    @Test
    public void testParallelCustomScope() throws Exception {
        triggerAsync(s -> appService.greetUser(s), "Marius", "Monica", "Test");

        Thread.sleep(wait_seconds * 1000L);
    }

    public void triggerAsync(Consumer<String> function, final String... payloads) throws InterruptedException {
        for (String payload : payloads) {
            executor.submit(() -> function.accept(payload));
        }
    }

}
