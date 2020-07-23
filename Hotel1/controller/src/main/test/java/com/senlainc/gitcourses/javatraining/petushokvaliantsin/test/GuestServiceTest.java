package com.senlainc.gitcourses.javatraining.petushokvaliantsin.test;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IGuestService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.AppConfig;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class GuestServiceTest {

    private IGuestService guestService;

    @Test
    public void testWrongIndexWhileDelete() {
        Assert.assertThrows(ElementNotFoundException.class, () -> guestService.delete(-2L));
    }

    @Test
    public void testWrongIndexWhileRead() {
        Assert.assertThrows(ElementNotFoundException.class, () -> guestService.read(-2L));
    }

    @Test
    public void testNegativeFirstElementWhileRead() {
        Assert.assertThrows(IllegalArgumentException.class, () -> guestService.readAll(-21, 5));
    }
}
