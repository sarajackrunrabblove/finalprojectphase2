package com.example.finalprojectphase2.service;

import com.example.finalprojectphase2.Exception.CustomException;
import com.example.finalprojectphase2.model.Offer;
import com.example.finalprojectphase2.model.Order;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.model.enums.OrderStatus;
import com.example.finalprojectphase2.repository.OfferRepository;
import com.example.finalprojectphase2.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OfferService implements BaseService<Offer> {
    private final OfferRepository repository;

    @Override
    public Offer save(Offer offer) {
        return this.repository.save(offer);
    }

    @Override
    public void update(Offer offer) {
        this.repository.save(offer);
    }

    public void delete(Offer offer) {
        this.repository.delete(offer);
    }

    @Override
    public Offer findById(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public List<Offer> findAll() {
        return this.repository.findAll();
    }

    public void addNewOffer(User expertUser, Order order, Float offeredPrice,
                            LocalDateTime offeredStartingTime,
                            Duration duration) {

        Float basePrice = order.getHomeService().getBasePrice();

        if (basePrice > offeredPrice)
            throw new CustomException("قیمت وارده باید بیشتر از بیس قیمت باشد");
        if (LocalTime.now().isAfter(LocalTime.from(offeredStartingTime)))
            throw new CustomException("زمان شروع بکار باید بیشتر از زمان حال باشد.");
        if (!Set.of(OrderStatus.WAITING_FOR_CHOOSING_EXPERT, OrderStatus.WAITING_FOR_EXPERT_OFFER).contains(order.getStatus()))
            throw new CustomException("وضعیت سفارش در حالت منتخب نمیباشد");
        Offer offer = new Offer();
        offer.setOfferedPrice(offeredPrice);
        offer.setOfferedStartingTime(offeredStartingTime);
        offer.setExpert(expertUser);
        offer.setOrder(order);
        offer.setDuration(duration);
        this.save(offer);
    }

    public Offer findByCustomerId(Long customerId) {
        return this.repository.findByExpertId(customerId).orElse(null);
    }

}