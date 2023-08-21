package com.example.finalprojectphase2.payload;

import com.example.finalprojectphase2.model.Order;
import com.example.finalprojectphase2.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {

    private User expertUser;

    private Order order;

    private Float offeredPrice;

    private LocalDateTime offeredStartingTime;

    private Duration duration;
}
