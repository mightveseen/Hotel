package com.senlainc.gitcourses.javatraining.petushokvaliantsin.service;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository.IGuestDao;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IGuestService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Guest;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@PropertySource(value = {"classpath:/properties/guest.properties"}, ignoreResourceNotFound = true)
public class GuestService implements IGuestService {

    private final IGuestDao guestDao;
    @Value(value = "${GUEST_CONFIG.GUEST_LIMIT_VALUE:100}")
    private int guestLimitProperty;

    @Autowired
    public GuestService(IGuestDao guestDao) {
        this.guestDao = guestDao;
    }

    @Override
    @Transactional
    public boolean create(Guest object) {
        if (guestLimitProperty < guestDao.readSize()) {
            throw new ElementNotAvailableException("The number of guest's exceeds the specified limit [" + guestLimitProperty + "] guest's");
        }
        guestDao.create(object);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Long index) {
        guestDao.delete(guestDao.read(index));
        return true;
    }

    @Override
    @Transactional
    public boolean update(Guest object) {
        guestDao.update(object);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNum() {
        return guestDao.readSize();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Guest read(Long index) {
        return guestDao.read(index);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Guest> readAll(int firstElement, int maxResult) {
        return guestDao.readAll(firstElement, maxResult);
    }
}
