package com.senlainc.gitcourses.javatraining.petushokvaliantsin.controller;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IOrderService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IRoomService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto.RoomDto;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;
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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "rooms")
public class RoomController {

    private final IRoomService roomService;
    private final IOrderService orderService;
    private final IDtoMapper dtoMapper;

    @Autowired
    public RoomController(IRoomService roomService, IOrderService orderService, IDtoMapper dtoMapper) {
        this.roomService = roomService;
        this.orderService = orderService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<RoomDto> showRooms(@RequestParam(value = "criteria", defaultValue = "all") String criteria,
                                   @RequestParam(value = "start", defaultValue = "0") @PositiveOrZero int firstElement,
                                   @RequestParam(value = "limit", defaultValue = "15") @PositiveOrZero int maxResult) {
        return dtoMapper.mapAll(roomService.readAll(criteria, firstElement, maxResult), RoomDto.class);
    }

    @GetMapping(value = "/sorted-rooms")
    public List<RoomDto> showRooms(@RequestParam(value = "criteria", defaultValue = "all") String criteria,
                                   @RequestParam(value = "sort", defaultValue = "default") String sort,
                                   @RequestParam(value = "start", defaultValue = "0") @PositiveOrZero int firstElement,
                                   @RequestParam(value = "limit", defaultValue = "15") @PositiveOrZero int maxResult) {
        return dtoMapper.mapAll(roomService.readAll(criteria, firstElement, maxResult, sort), RoomDto.class);
    }

    @GetMapping(value = "/{id}")
    public RoomDto showRoom(@PathVariable(value = "id") @Positive long index) {
        return dtoMapper.map(roomService.read(index), RoomDto.class);
    }

    @GetMapping(value = "/num-free")
    public Long showNumFree() {
        return roomService.getNumFree();
    }

    @GetMapping(value = "/after-date")
    public List<RoomDto> showAfterDate(@RequestParam("date") LocalDate date,
                                       @RequestParam(value = "start", defaultValue = "0") @PositiveOrZero int firstElement,
                                       @RequestParam(value = "limit", defaultValue = "15") @PositiveOrZero int maxResult) {
        return dtoMapper.mapAll(orderService.getRoomsAfterDate(date, firstElement, maxResult), RoomDto.class);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteRoom(@PathVariable(value = "id") @Positive long index) {
        roomService.delete(index);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateRoom(@RequestBody @Validated(RoomDto.Update.class) RoomDto request) {
        roomService.update(dtoMapper.map(request, Room.class));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createRoom(@RequestBody @Validated(RoomDto.Create.class) RoomDto request) {
        roomService.create(dtoMapper.map(request, Room.class));
    }
}
