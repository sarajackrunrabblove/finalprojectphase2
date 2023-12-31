package com.example.finalprojectphase2.controller;

import com.example.finalprojectphase2.model.Offer;
import com.example.finalprojectphase2.model.Order;
import com.example.finalprojectphase2.payload.OrderDTO;
import com.example.finalprojectphase2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/create-order")
    public ResponseEntity<Order> createOrder(@RequestBody  OrderDTO order) {
        return ResponseEntity.ok(orderService.newOrder(order));
    }

    @GetMapping(value = "/get-order")
    public ResponseEntity<Order> getOrder(@RequestParam Long customerId) {
        return ResponseEntity.ok(orderService.findByCustomerId(customerId));
    }

    @DeleteMapping(value = "/delete-order")
    public void deleteOrder(@RequestParam Long customerId) {
        orderService.delete(orderService.findByCustomerId(customerId));
    }

    @PostMapping(value = "/update-order")
    public void updateOrder(@RequestBody Order order) {
        orderService.update(order);
    }

    @GetMapping(value = "/showOffersByPriceAndExpertRate")
    public List<Offer> showOffersByPriceAndExpertRate(
            @RequestParam(required = true) Long orderId,
            @RequestParam(required = false) Float price,
            @RequestParam(required = false) Integer expertRate
    ) {
        return orderService.showOffersByPriceAndExpertRate(orderId, price, expertRate);
    }
// دیدن سفارشات در وضعیت انتظار پیشنهاد مختصص
    @GetMapping(value = "/showOffersByExpert")
    public List<Order> showOffersByExpert(@RequestParam Long expertId) {
        return orderService.showOffersByExpert(expertId);
    }
    @PostMapping(value = "/rateExpertFinalOffer")
    public ResponseEntity<?> rateExpertFinalOffer(@RequestParam Long orderId, @RequestParam Integer rate, @RequestParam String description) {
        Order byId = orderService.findById(orderId);
        orderService.rateExpertFinalOffer(byId, rate, description);
        return  ResponseEntity.ok("امیتاز به متخصص مربوطه انجام شد ");
    }

}
