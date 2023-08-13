package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.model.*;
import com.example.finalprojectphase2.model.enums.OrderStatus;
import com.example.finalprojectphase2.repository.OrderRepository;
import com.example.finalprojectphase2.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService implements BaseService<Order> {
    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }

    public void delete(Order order) {
        this.orderRepository.delete(order);
    }

    @Override
    public Order findById(Long id) {
        return this.orderRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    public void newOrder(User customer, ServiceItem service,
                         String address, Float customerPrice,
                         String description, LocalDateTime startingTime) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setHomeService(service);
        order.setAddress(address);
        order.setCustomerPrice(customerPrice);
        order.setCreatorUser(customer);
        order.setDescription(description);
        order.setStartingTime(startingTime);
        this.save(order);
    }

    public Order findByCustomerId(Long customerId) {
        return this.orderRepository.findByCustomerId(customerId).orElse(null);
    }

    public void changeStatusTo(Order order,OrderStatus status){
        order.setStatus(status);
        orderRepository.save(order);
    }
}
