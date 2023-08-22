package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.exception.CustomException;
import com.example.finalprojectphase2.model.*;
import com.example.finalprojectphase2.model.enums.OrderStatus;
import com.example.finalprojectphase2.model.enums.PaymentSelection;
import com.example.finalprojectphase2.payload.OrderDTO;
import com.example.finalprojectphase2.repository.OfferRepository;
import com.example.finalprojectphase2.repository.OrderRepository;
import com.example.finalprojectphase2.repository.UserRepository;
import com.example.finalprojectphase2.util.CustomerPaymentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {
    private final OrderRepository orderRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final CustomerPaymentUtil customerPaymentUtil;


    public Order save(Order order) {
        return this.orderRepository.save(order);
    }


    public void update(Order order) {
        this.orderRepository.save(order);
    }

    public void delete(Order order) {
        this.orderRepository.delete(order);
    }


    public Order findById(Long id) {
        return this.orderRepository.findById(id).orElseThrow();
    }


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


    public Order newOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomer(orderDTO.getCustomer());
        order.setHomeService(orderDTO.getService());
        order.setAddress(orderDTO.getAddress());
        order.setCustomerPrice(orderDTO.getCustomerPrice());
        order.setCreatorUser(orderDTO.getCustomer());
        order.setDescription(orderDTO.getDescription());
        order.setStartingTime(orderDTO.getStartingTime());
        this.save(order);
        return order;
    }

    public Order findByCustomerId(Long customerId) {
        return this.orderRepository.findByCustomerId(customerId).orElse(null);
    }

    public void changeStatusTo(Order order, OrderStatus status) {
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void setFinalOfferForOrder(Order order, Offer finalOffer) {

        orderRepository.findByCustomerId(order.getCustomer().getId()).ifPresent(
                customerOrder -> {
                    customerOrder.setFinalOffer(finalOffer);
                    orderRepository.save(customerOrder);
                }
        );
    }

    public void payForExpert(Order order) {
        Float customerCredit = order.getCustomer().getCredit();
        if (customerCredit < order.getFinalOffer().getOfferedPrice())
            throw new CustomException("  وجه اعتبار شما کمتر ازمبلغ پرداختی میباشد لطفا ابتدا اعتبار خود را افزایش دهید");

        float finalCustomerCredit = customerCredit - order.getFinalOffer().getOfferedPrice();
        userRepository.findByUserName(order.getCustomer().getUserName()).ifPresent(
                user -> {
                    user.setCredit(finalCustomerCredit);
                    userRepository.save(user);
                }
        );
        userRepository.findByUserName(order.getFinalOffer().getExpert().getUserName()).ifPresent(
                user -> {
                    float expertNewCredit = user.getCredit() + order.getFinalOffer().getOfferedPrice();
                    user.setCredit(expertNewCredit);
                    userRepository.save(user);
                }
        );
    }

    public ResponseEntity<Object> payForExperts(Order order, PaymentSelection paymentSelection) {
        if (paymentSelection.equals(PaymentSelection.PayWithCredit)) {
            return customerPaymentUtil.payWithCredit(order);
        }
        return customerPaymentUtil.payOnline(order);
    }

    public List<Offer> showOffersByPriceAndExpertRate(Long orderId, Float price, Integer expertRate) {
        return offerRepository.findAllByOfferedPriceOrExpertRateAndOrderId(price, expertRate, orderId);
    }

    public List<Order> showOffersByExpert(Long expertId) {

        return orderRepository.findAllByExpertId(expertId, OrderStatus.WAITING_FOR_EXPERT_OFFER);

    }
}
