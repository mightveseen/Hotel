package com.senlainc.gitcourses.javatraining.petushokvaliantsin.service;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository.IRoomDao;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IRoomService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room_;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.RoomStatus;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementAlreadyExistsException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotAvailableException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.singularattribute.ISingularMapper;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.singularattribute.annotations.SingularClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@PropertySource(value = {"classpath:/properties/room.properties"}, ignoreResourceNotFound = true)
public class RoomService implements IRoomService {

    private final IRoomDao roomDao;
    private final ISingularMapper singularMapper;
    @Value("${ROOM_CONFIG.CHANGE_STATUS_VALUE:true}")
    private boolean changeStatusProperty;

    @Autowired
    public RoomService(IRoomDao roomDao, ISingularMapper singularMapper) {
        this.roomDao = roomDao;
        this.singularMapper = singularMapper;
    }

    @Override
    @Transactional
    public boolean create(Room object) {
        if (roomDao.readByNumber(object.getNumber())) {
            throw new ElementAlreadyExistsException("Room with number [" + object.getNumber() + "] already exists.");
        }
        object.setStatus(RoomStatus.FREE);
        roomDao.create(object);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Long index) {
        roomDao.delete(roomDao.read(index));
        return true;
    }

    @Override
    @Transactional
    public boolean update(Room object) {
        roomDao.update(object);
        return true;
    }

    @Override
    @Transactional
    public void changeStatus(long index, String status) {
        if (changeStatusProperty) {
            final Room room = roomDao.read(index);
            room.setStatus(RoomStatus.valueOf(status));
            roomDao.update(room);
        } else {
            throw new ElementNotAvailableException("Property for change status is false");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNumFree() {
        return roomDao.readFreeSize();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public RoomStatus getRoomStatus(long index) {
        return roomDao.readStatus(index);
    }

    @Override
    @Transactional(readOnly = true)
    public Double gerRoomPrice(long index) {
        return roomDao.readPrice(index);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Room read(Long index) {
        return roomDao.read(index);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> readAll(int firstElement, int maxResult) {
        return roomDao.readAll(firstElement, maxResult, Room_.id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> readAll(String criteria, int firstElement, int maxResult) {
        if (criteria.equals("free")) {
            return roomDao.readAll(Room_.status, RoomStatus.FREE, firstElement, maxResult);
        }
        return roomDao.readAll(firstElement, maxResult);
    }

    @Override
    @Transactional(readOnly = true)
    @SingularClasses(metaModels = Room_.class)
    public List<Room> readAll(String criteria, int firstElement, int maxResult, String sortParameter) {
        if (sortParameter.equals("default")) {
            return readAll(criteria, firstElement, maxResult);
        }
        singularMapper.setClass(RoomService.class);
        if (criteria.equals("free")) {
            return roomDao.readAll(Room_.status, RoomStatus.FREE, firstElement, maxResult, singularMapper.getSingularAttribute(sortParameter));
        }
        return roomDao.readAll(firstElement, maxResult, singularMapper.getSingularAttribute(sortParameter));
    }
}
