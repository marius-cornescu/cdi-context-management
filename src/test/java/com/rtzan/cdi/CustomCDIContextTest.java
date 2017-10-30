package com.rtzan.cdi;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
@RunWith(Arquillian.class)
public class CustomCDIContextTest {

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
                        .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
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

    @Test
    public void testOneCall() throws Exception {
        // Fire synchronous event that triggers the code in App class.
        appService.greetMe("Marius");
    }

    @Test
    public void testParallelCall() throws Exception {
        triggerAsync(s -> appService.greetMe(s), "Marius", "Monica", "Test");

        Thread.sleep(30*1000L);
    }

    @Test
    public void testParallelAnnotatedCall() throws Exception {
        triggerAsync(s -> appService.greet2(s), "Marius", "Monica", "Test");

        Thread.sleep(30*1000L);
    }

    public void triggerAsync(Consumer<String> function, final String... payloads) throws InterruptedException {
        for (String payload : payloads) {
            executor.submit(() -> function.accept(payload));
        }
    }

}
