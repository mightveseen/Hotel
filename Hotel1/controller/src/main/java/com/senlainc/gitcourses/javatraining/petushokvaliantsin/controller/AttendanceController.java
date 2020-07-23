package com.senlainc.gitcourses.javatraining.petushokvaliantsin.controller;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IAttendanceService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto.AttendanceDto;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Attendance;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.dto.IDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "attendances")
public class AttendanceController {

    private final IAttendanceService attendanceService;
    private final IDtoMapper dtoMapper;

    @Autowired
    public AttendanceController(IAttendanceService attendanceService, IDtoMapper dtoMapper) {
        this.attendanceService = attendanceService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<AttendanceDto> showAttendances(@RequestParam(value = "start", defaultValue = "0") @PositiveOrZero int firstElement,
                                               @RequestParam(value = "limit", defaultValue = "15") @PositiveOrZero int maxResult) {
        return dtoMapper.mapAll(attendanceService.readAll(firstElement, maxResult), AttendanceDto.class);
    }

    @GetMapping(value = "/sorted-attendances")
    public List<AttendanceDto> showAttendances(@RequestParam(value = "sort", defaultValue = "default") String sort,
                                               @RequestParam(value = "start", defaultValue = "0") @PositiveOrZero int firstElement,
                                               @RequestParam(value = "limit", defaultValue = "15") @PositiveOrZero int maxResult) {
        return dtoMapper.mapAll(attendanceService.readAll(firstElement, maxResult, sort), AttendanceDto.class);
    }

    @GetMapping(value = "/{id}")
    public AttendanceDto showAttendance(@PathVariable(value = "id") @Positive long index) {
        return dtoMapper.map(attendanceService.read(index), AttendanceDto.class);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAttendance(@PathVariable(value = "id") @Positive long index) {
        attendanceService.delete(index);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAttendance(@RequestBody @Validated(AttendanceDto.Update.class) AttendanceDto request) {
        attendanceService.update(dtoMapper.map(request, Attendance.class));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createAttendance(@RequestBody @Validated(AttendanceDto.Create.class) AttendanceDto request) {
        attendanceService.create(dtoMapper.map(request, Attendance.class));
    }
}
