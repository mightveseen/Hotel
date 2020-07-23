package com.senlainc.gitcourses.javatraining.petushokvaliantsin.test;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IOrderService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.AppConfig;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Guest;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Order;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.OrderStatus;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.RoomStatus;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotAvailableException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotFoundException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.dao.ReadQueryException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class OrderServiceTest {

    private IOrderService orderService;

    @Test
    public void testWrongRoomIndexWhileCreateOrder() {
        final Room room = new Room(333, "Try", (short) 2, (short) 4, RoomStatus.FREE, 156.0);
        final Guest guest = new Guest("Try", "Try", LocalDate.now());
        Assert.assertThrows(ReadQueryException.class, () -> orderService.create(new Order(room, guest, LocalDate.now(), LocalDateTime.now(), LocalDate.now(), OrderStatus.ACTIVE, room.getPrice())));
    }

    @Test
    public void testWrongGuestIndexWhileCreateOrder() {
        final Room room = new Room(333, "Try", (short) 2, (short) 4, RoomStatus.FREE, 156.0);
        room.setId(3L);
        final Guest guest = new Guest("Try", "Try", LocalDate.now());
        Assert.assertThrows(ElementNotFoundException.class, () -> orderService.create(new Order(room, guest, LocalDate.now(), LocalDateTime.now(), LocalDate.now(), OrderStatus.ACTIVE, room.getPrice())));
    }

    @Test
    public void testRoomNotAvailableWhileCreateOrder() {
        final Room room = new Room(333, "Try", (short) 2, (short) 4, RoomStatus.FREE, 156.0);
        room.setId(5L);
        final Guest guest = new Guest("Try", "Try", LocalDate.now());
        Assert.assertThrows(ElementNotAvailableException.class, () -> orderService.create(new Order(room, guest, LocalDate.now(), LocalDateTime.now(), LocalDate.now(), OrderStatus.ACTIVE, room.getPrice())));
    }

    @Test
    public void testDeleteOrder() {
        orderService.read(2L).setStatus(OrderStatus.DISABLED);
        Assert.assertThrows(ElementNotAvailableException.class, () -> orderService.delete(2L));
    }

    @Test
    public void testAddAttendanceToDisabledOrder() {
        orderService.read(2L).setStatus(OrderStatus.DISABLED);
        Assert.assertThrows(ElementNotAvailableException.class, () -> orderService.addAttendance(2L, 1L));
    }

    @Test
    public void testAddAttendanceWrongAttendanceIndex() {
        orderService.read(2L).setStatus(OrderStatus.ACTIVE);
        Assert.assertThrows(ElementNotFoundException.class, () -> orderService.addAttendance(2L, -1L));
    }

    @Test
    public void testReadGuestRoomWrongIndex() {
        Assert.assertNotNull(orderService.getGuestRooms(-2L, 15));
    }

    @Test
    public void testReadOrderAttendances() {
        Assert.assertNotNull(orderService.getAttendances(2L));
    }

    @Test
    public void testReadSortedWithWrongParameter() {
        Assert.assertThrows(IllegalArgumentException.class, () -> orderService.readAll(0, 15, "fffff"));
    }
}
