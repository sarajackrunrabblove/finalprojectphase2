package com.example.finalprojectphase2.repository;

import com.example.finalprojectphase2.model.Offer;
import com.example.finalprojectphase2.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Optional<Offer> findByExpertId(Long expertId);

    List<Offer> findAllByOfferedPriceOrExpertRateAndOrderId(Float offeredPrice, Integer expertRate, Long orderId);

    @Query("select avg(o.expertRate) from Offer o where o.expert.id = :expertId")
    Integer getExpertRateAvg(@Param("expertId") Long expertId);

}
