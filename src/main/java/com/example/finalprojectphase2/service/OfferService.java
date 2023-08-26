package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.exception.CustomException;
import com.example.finalprojectphase2.model.Offer;
import com.example.finalprojectphase2.model.Order;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.OrderStatus;
import com.example.finalprojectphase2.payload.OfferDTO;
import com.example.finalprojectphase2.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OfferService {
    private final OfferRepository repository;

    
    public Offer save(Offer offer) {
        return this.repository.save(offer);
    }

    
    public void update(Offer offer) {
        this.repository.save(offer);
    }

    public void delete(Offer offer) {
        this.repository.delete(offer);
    }

    
    public Offer findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    
    public List<Offer> findAll() {
        return this.repository.findAll();
    }

    public void addNewOffer(User expertUser, Order order, Float offeredPrice,
                            LocalDateTime offeredStartingTime,
                            Duration duration) {

        Float basePrice = order.getHomeService().getBasePrice();

        if (basePrice > offeredPrice)
            throw new CustomException("قیمت وارده باید بیشتر از بیس قیمت باشد", HttpStatus.BAD_REQUEST);
        if (LocalTime.now().isAfter(LocalTime.from(offeredStartingTime)))
            throw new CustomException("زمان شروع بکار باید بیشتر از زمان حال باشد.", HttpStatus.BAD_REQUEST);
        if (!Set.of(OrderStatus.WAITING_FOR_CHOOSING_EXPERT, OrderStatus.WAITING_FOR_EXPERT_OFFER).contains(order.getStatus()))
            throw new CustomException("وضعیت سفارش در حالت منتخب نمیباشد", HttpStatus.BAD_REQUEST);
        Offer offer = new Offer();
        offer.setOfferedPrice(offeredPrice);
        offer.setOfferedStartingTime(offeredStartingTime);
        offer.setExpert(expertUser);
        offer.setOrder(order);
        offer.setDuration(duration);
        this.save(offer);
    }

    public Offer addNewOffer(OfferDTO offerDTO) {

        Float basePrice = offerDTO.getOrder().getHomeService().getBasePrice();

        if (basePrice > offerDTO.getOfferedPrice())
            throw new CustomException("قیمت وارده باید بیشتر از بیس قیمت باشد", HttpStatus.BAD_REQUEST);
        if (LocalTime.now().isAfter(LocalTime.from(offerDTO.getOfferedStartingTime())))
            throw new CustomException("زمان شروع بکار باید بیشتر از زمان حال باشد.", HttpStatus.BAD_REQUEST);
        if (!Set.of(OrderStatus.WAITING_FOR_CHOOSING_EXPERT, OrderStatus.WAITING_FOR_EXPERT_OFFER).contains(offerDTO.getOrder().getStatus()))
            throw new CustomException("وضعیت سفارش در حالت منتخب نمیباشد", HttpStatus.BAD_REQUEST);
        Offer offer = new Offer();
        offer.setOfferedPrice(offerDTO.getOfferedPrice());
        offer.setOfferedStartingTime(offerDTO.getOfferedStartingTime());
        offer.setExpert(offerDTO.getExpertUser());
        offer.setOrder(offer.getOrder());
        offer.setDuration(offerDTO.getDuration());
        this.save(offer);
        return offer;
    }

    public Offer findByCustomerId(Long customerId) {
        return this.repository.findByExpertId(customerId).orElse(null);
    }

}