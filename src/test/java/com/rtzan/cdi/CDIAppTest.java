package com.rtzan.cdi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

/**
 * Created by ${USERNAME} on 9/20/17.
 */
public class CDIAppTest {

    private SeContainer container;

    @Before
    public void setUp() throws Exception {
        SeContainerInitializer containerInit = SeContainerInitializer.newInstance();
        container = containerInit.initialize();
    }

    @After
    public void tearDown() throws Exception {
        container.close();
    }

    @Test
    public void onEvent() throws Exception {
        // Fire synchronous event that triggers the code in App class.
        container.getBeanManager().fireEvent(new SimpleEvent());
    }

}