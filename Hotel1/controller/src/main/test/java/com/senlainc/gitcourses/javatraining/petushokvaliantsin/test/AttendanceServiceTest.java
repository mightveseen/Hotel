package com.senlainc.gitcourses.javatraining.petushokvaliantsin.test;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IAttendanceService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AttendanceServiceTest {

    private IAttendanceService attendanceService;

    @Test
    public void testWrongIndexWhileDelete() {
        Assert.assertThrows(ElementNotFoundException.class, () -> attendanceService.delete(-2L));
    }

    @Test
    public void testWrongIndexWhileRead() {
        Assert.assertThrows(ElementNotFoundException.class, () -> attendanceService.read(-2L));
    }

    @Test
    public void testWrongIndexWhileChangePrice() {
        Assert.assertThrows(ElementNotFoundException.class, () -> attendanceService.changePrice(-2L, 22.5));
    }

    @Test
    public void testWrongParameterWhileReadSorted() {
        Assert.assertThrows(IllegalArgumentException.class, () -> attendanceService.readAll(1, 5, "ff"));
    }

    @Test
    public void testNegativeFirstElementWhileReadSorted() {
        Assert.assertThrows(IllegalArgumentException.class, () -> attendanceService.readAll(-21, 5, "default"));
    }
}
