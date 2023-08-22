package com.example.finalprojectphase2.repository;

import com.example.finalprojectphase2.model.Order;
import com.example.finalprojectphase2.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByCustomerId(Long customerId);

    @Query(value = "from Order  where finalOffer.expert.id =: expertId or status =:  orderStatus")
    List<Order> findAllByExpertId(@Param("expertId") Long expertId, @Param("orderStatus")OrderStatus orderStatus);
}
