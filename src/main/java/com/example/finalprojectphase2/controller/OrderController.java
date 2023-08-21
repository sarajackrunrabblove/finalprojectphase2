package com.example.finalprojectphase2.controller;

import com.example.finalprojectphase2.model.Order;
import com.example.finalprojectphase2.payload.OrderDTO;
import com.example.finalprojectphase2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/create-order")
    public ResponseEntity<Order> createOrder(OrderDTO order) {
        return ResponseEntity.ok(orderService.newOrder(order));
    }

    @GetMapping(value = "/get-order")
    public ResponseEntity<Order> getOrder(Long customerId) {
        return ResponseEntity.ok(orderService.findByCustomerId(customerId));
    }

    @DeleteMapping(value = "/delete-order")
    public void deleteOrder(Long customerId) {
        orderService.delete(orderService.findByCustomerId(customerId));
    }

    @PostMapping(value = "/update-order")
    public void updateOrder(Order order) {
        orderService.update(order);
    }
}