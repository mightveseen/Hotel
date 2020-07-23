package com.senlainc.gitcourses.javatraining.petushokvaliantsin.service;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository.IOrderDao;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IAttendanceService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IGuestService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IOrderService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IRoomService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Attendance;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Guest;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Guest_;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Order;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Order_;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room_;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.OrderStatus;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.RoomStatus;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotAvailableException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotFoundException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.singularattribute.ISingularMapper;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.singularattribute.annotations.SingularClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final IOrderDao orderDao;
    private final IAttendanceService attendanceService;
    private final IRoomService roomService;
    private final IGuestService guestService;
    private final ISingularMapper singularMapper;

    @Autowired
    public OrderService(IOrderDao orderDao, IAttendanceService attendanceService, IRoomService roomService,
                        IGuestService guestService, ISingularMapper singularMapper) {
        this.orderDao = orderDao;
        this.attendanceService = attendanceService;
        this.roomService = roomService;
        this.guestService = guestService;
        this.singularMapper = singularMapper;
    }

    @Override
    @Transactional
    public boolean create(Order order) {
        final RoomStatus roomStatus = roomService.getRoomStatus(order.getRoom().getId());
        if (roomStatus.equals(RoomStatus.RENTED) || roomStatus.equals(RoomStatus.SERVED)) {
            throw new ElementNotAvailableException("Room with index [" + order.getRoom().getId() + "] is not available. " +
                    "Room status [" + roomStatus + "].");
        }
        final Room room = roomService.read(order.getRoom().getId());
        final Guest guest = guestService.read(order.getGuest().getId());
        order.setOrderDate(LocalDateTime.now());
        order.setStartDate(LocalDate.now());
        order.setStatus(OrderStatus.ACTIVE);
        order.setRoom(room);
        order.setGuest(guest);
        order.setPrice(room.getPrice());
        orderDao.create(order);
        roomService.changeStatus(order.getRoom().getId(), RoomStatus.RENTED.name());
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Long index) {
        final Order order = orderDao.read(index);
        if (order.getStatus().equals(OrderStatus.DISABLED)) {
            throw new ElementNotAvailableException("Order with index [" + index + "] already disabled.");
        }
        order.setStatus(OrderStatus.DISABLED);
        order.setEndDate(LocalDate.now());
        orderDao.update(order);
        roomService.changeStatus(order.getRoom().getId(), RoomStatus.FREE.name());
        return true;
    }

    @Override
    @Transactional
    public boolean update(Order order) {
        orderDao.update(order);
        return true;
    }

    @Override
    @Transactional
    public void addAttendance(long orderIndex, long attendanceIndex) {
        final Order order = orderDao.read(orderIndex);
        if (order.getStatus().equals(OrderStatus.DISABLED)) {
            throw new ElementNotAvailableException("Order with index [" + orderIndex + "] is disabled.");
        }
        final Attendance attendance = attendanceService.read(attendanceIndex);
        final List<Attendance> attendances;
        if (order.getAttendances() == null) {
            attendances = new ArrayList<>();
        } else {
            attendances = order.getAttendances();
        }
        attendances.add(attendance);
        order.setAttendances(attendances);
        order.setPrice(order.getPrice() + attendance.getPrice());
        orderDao.update(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getGuestRooms(long index, int limit) {
        final List<Room> rooms = orderDao.readLastRoom(index, limit);
        if (rooms == null) {
            return Collections.emptyList();
        }
        return rooms;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsAfterDate(LocalDate date, int firstElement, int maxResult) {
        final List<Room> rooms = roomService.readAll("free", firstElement, maxResult);
        rooms.addAll(orderDao.readAfterDate(date));
        return rooms;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attendance> getAttendances(long orderIndex) {
        final List<Attendance> attendances = orderDao.read(orderIndex).getAttendances();
        if (attendances == null) {
            throw new ElementNotFoundException("Order with index [" + orderIndex + "] don't have attendance's.");
        }
        return attendances;
    }

    @Override
    @Transactional(readOnly = true)
    public Order read(Long index) {
        return orderDao.read(index);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> readAll(int firstElement, int maxResult) {
        return orderDao.readAll(firstElement, maxResult);
    }

    @Override
    @Transactional(readOnly = true)
    @SingularClasses(metaModels = {Order_.class, Guest_.class, Room_.class})
    public List<Order> readAll(int firstElement, int maxResult, String sortParameter) {
        if (sortParameter.equals("default")) {
            return readAll(firstElement, maxResult);
        }
        singularMapper.setClass(OrderService.class);
        if (sortParameter.contains("-")) {
            final String[] parameterParse = sortParameter.split("-", 2);
            return orderDao.readAll(firstElement, maxResult, singularMapper.getSingularAttribute(parameterParse[0]),
                    singularMapper.getSingularAttribute(parameterParse[1]));
        }
        return orderDao.readAll(firstElement, maxResult, singularMapper.getSingularAttribute(sortParameter));
    }
}
