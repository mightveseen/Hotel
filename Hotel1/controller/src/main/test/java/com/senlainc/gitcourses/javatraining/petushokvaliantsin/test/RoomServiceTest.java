package com.senlainc.gitcourses.javatraining.petushokvaliantsin.test;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IRoomService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.AppConfig;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.RoomStatus;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementAlreadyExistsException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotFoundException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.dao.ReadQueryException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RoomServiceTest {

    private IRoomService roomService;

    @Test
    public void testCreateRoomWithExistNumber() {
        Assert.assertThrows(ElementAlreadyExistsException.class, () -> roomService.create(new Room(105, "Try", (short) 2, (short) 4, RoomStatus.FREE, 156.0)));
    }

    @Test
    public void testWrongIndexWhileDelete() {
        Assert.assertThrows(ElementNotFoundException.class, () -> roomService.delete(-2L));
    }

    @Test
    public void testWrongIndexWhileRead() {
        Assert.assertThrows(ElementNotFoundException.class, () -> roomService.read(-2L));
    }

    @Test
    public void testWrongIndexWhileChangeStatus() {
        Assert.assertThrows(ElementNotFoundException.class, () -> roomService.changeStatus(-2L, RoomStatus.RENTED.name()));
    }

    @Test
    public void testGetRoomStatusWithWrongIndex() {
        Assert.assertThrows(ReadQueryException.class, () -> roomService.getRoomStatus(-2L));
    }

    @Test
    public void testGetPriceWithWrongIndex() {
        Assert.assertThrows(ReadQueryException.class, () -> roomService.gerRoomPrice(-2L));
    }

    @Test
    public void testReadAllWithWrongType() {
        Assert.assertNotNull(roomService.readAll("fff", 0, 15));
    }

    @Test
    public void testReadAllWithWrongFirstElement() {
        Assert.assertThrows(IllegalArgumentException.class, () -> roomService.readAll("fff", -2, 15));
    }
}
