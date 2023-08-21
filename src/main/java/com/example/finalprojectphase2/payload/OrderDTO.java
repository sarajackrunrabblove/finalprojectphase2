package com.example.finalprojectphase2.payload;

import com.example.finalprojectphase2.model.ServiceItem;
import com.example.finalprojectphase2.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private User customer;
    private ServiceItem service;

    private String address;
    private Float customerPrice;

    private String description;
    private LocalDateTime startingTime;
}
