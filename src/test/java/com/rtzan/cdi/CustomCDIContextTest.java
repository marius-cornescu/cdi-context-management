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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
@RunWith(Arquillian.class)
public class CustomCDIContextTest {

    private ExecutorService executor;

    @Inject
    private SimpleService simpleService;

    @Deployment
    public static JavaArchive deployment() {
        JavaArchive archive =
                ShrinkWrap.create(JavaArchive.class)
                        // Test classes
                        .addPackages(true, SimpleService.class.getPackage())
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
        simpleService.greetMe("Marius");
    }

    @Test
    public void testParallelCall() throws Exception {
        triggerAsync("Marius", "Monica", "Test");

        Thread.sleep(30*1000L);
    }

    public void triggerAsync(final String... payloads) throws InterruptedException {
        for (String payload : payloads) {
            executor.submit(() -> simpleService.greetMe(payload));
        }
    }

}