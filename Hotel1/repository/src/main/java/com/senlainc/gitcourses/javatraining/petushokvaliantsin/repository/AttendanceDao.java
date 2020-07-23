package com.senlainc.gitcourses.javatraining.petushokvaliantsin.repository;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository.IAttendanceDao;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Attendance;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDao extends AbstractDao<Attendance, Long> implements IAttendanceDao {

}
