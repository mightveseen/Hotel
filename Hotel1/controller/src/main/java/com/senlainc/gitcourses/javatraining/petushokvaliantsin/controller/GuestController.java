package com.senlainc.gitcourses.javatraining.petushokvaliantsin.controller;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IGuestService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IOrderService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto.GuestDto;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto.RoomDto;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Guest;
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
@RequestMapping(path = "guests")
public class GuestController {

    private final IGuestService guestService;
    private final IOrderService orderService;
    private final IDtoMapper dtoMapper;

    @Autowired
    public GuestController(IGuestService guestService, IOrderService orderService, IDtoMapper dtoMapper) {
        this.guestService = guestService;
        this.orderService = orderService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<GuestDto> showGuests(@RequestParam(value = "start", defaultValue = "0") @PositiveOrZero int firstElement,
                                     @RequestParam(value = "limit", defaultValue = "15") @PositiveOrZero int maxResult) {
        return dtoMapper.mapAll(guestService.readAll(firstElement, maxResult), GuestDto.class);
    }

    @GetMapping(value = "/{id}")
    public GuestDto showGuest(@PathVariable(value = "id") @Positive long index) {
        return dtoMapper.map(guestService.read(index), GuestDto.class);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteGuest(@PathVariable(value = "id") @Positive long index) {
        guestService.delete(index);
    }

    @GetMapping(value = "/{id}/last-rooms")
    public List<RoomDto> showLastRooms(@PathVariable(value = "id") @Positive long index,
                                       @RequestParam(value = "lm", defaultValue = "3") @Positive int limit) {
        return dtoMapper.mapAll(orderService.getGuestRooms(index, limit), RoomDto.class);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateGuest(@RequestBody @Validated(GuestDto.Update.class) GuestDto request) {
        guestService.update(dtoMapper.map(request, Guest.class));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createGuest(@RequestBody @Validated(GuestDto.Create.class) GuestDto request) {
        guestService.create(dtoMapper.map(request, Guest.class));
    }
}
