package com.example.finalprojectphase2.model;

import com.example.finalprojectphase2.model.base.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OnlinePayment extends BaseModel {


    private Long orderId;

	private String customerCredit;

    private String cardNumber;

    private String cvv2;

    private String expireData;

    private String captcha;

    private String hiddenCaptcha;

    private String realCaptcha;

    private LocalDateTime now;
}
