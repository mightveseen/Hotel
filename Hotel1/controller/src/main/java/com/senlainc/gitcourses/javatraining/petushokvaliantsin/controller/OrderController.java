package com.senlainc.gitcourses.javatraining.petushokvaliantsin.controller;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.IOrderService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto.AttendanceDto;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto.OrderDto;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Order;
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
@RequestMapping(path = "orders")
public class OrderController {

    private final IOrderService orderService;
    private final IDtoMapper dtoMapper;

    @Autowired
    public OrderController(IOrderService orderService, IDtoMapper dtoMapper) {
        this.orderService = orderService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<OrderDto> showOrders(@RequestParam(value = "start", defaultValue = "0") @PositiveOrZero int firstElement,
                                     @RequestParam(value = "limit", defaultValue = "15") @PositiveOrZero int maxResult) {
        return dtoMapper.mapAll(orderService.readAll(firstElement, maxResult), OrderDto.class);
    }

    @GetMapping(value = "/sorted-orders")
    public List<OrderDto> showOrders(@RequestParam(value = "sort", defaultValue = "default") String sort,
                                     @RequestParam(value = "start", defaultValue = "0") @PositiveOrZero int firstElement,
                                     @RequestParam(value = "limit", defaultValue = "15") @PositiveOrZero int maxResult) {
        return dtoMapper.mapAll(orderService.readAll(firstElement, maxResult, sort), OrderDto.class);
    }

    @GetMapping(value = "/{id}")
    public OrderDto showOrder(@PathVariable(value = "id") @Positive long index) {
        return dtoMapper.map(orderService.read(index), OrderDto.class);
    }

    @GetMapping(value = "/{id}/attendances")
    public List<AttendanceDto> showAttendances(@PathVariable(value = "id") @Positive long index) {
        return dtoMapper.mapAll(orderService.getAttendances(index), AttendanceDto.class);
    }

    @PutMapping(value = "/{id}/attendances/{id2}")
    public void addAttendance(@PathVariable(value = "id") @Positive long orderIndex,
                              @PathVariable(value = "id2") @Positive long attendanceIndex) {
        orderService.addAttendance(orderIndex, attendanceIndex);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteOrder(@PathVariable(value = "id") @Positive long index) {
        orderService.delete(index);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateOrder(@RequestBody @Validated(OrderDto.Update.class) OrderDto request) {
        orderService.update(dtoMapper.map(request, Order.class));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody @Validated(OrderDto.Create.class) OrderDto request) {
        orderService.create(dtoMapper.map(request, Order.class));
    }
}
