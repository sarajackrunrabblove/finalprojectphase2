package com.example.finalprojectphase2.controller;

import cn.apiclub.captcha.Captcha;
import com.example.finalprojectphase2.model.OnlinePayment;
import com.example.finalprojectphase2.model.Order;
import com.example.finalprojectphase2.model.enums.OrderStatus;
import com.example.finalprojectphase2.service.OrderService;
import com.example.finalprojectphase2.util.CaptchaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class PaymentOnlineController {
    private final OrderService orderService;

    @GetMapping(value = "/byCredit")
    public ResponseEntity<?> payForExperts(@RequestParam Long orderId) {
        Order byId = orderService.findById(orderId);
        orderService.payForExperts( byId,"PayWithCredit");
        orderService.changeStatusTo(byId, OrderStatus.DONE);
        return ResponseEntity.ok("payment success");

    }

    @GetMapping("/online")
    public String registerUser(Model model, @RequestParam Long orderId) {
        //TODO UNCOMMENT
        String offeredPrice = orderService.findById(orderId).getFinalOffer().getOfferedPrice().toString();
        OnlinePayment onlinePayment = new OnlinePayment();
        onlinePayment.setCustomerCredit(offeredPrice);
        onlinePayment.setOrderId(orderId);
        onlinePayment.setNow(LocalDateTime.now());
        getCaptcha(onlinePayment);
        model.addAttribute("paymentData", onlinePayment);
        model.addAttribute("customerCredit", onlinePayment.getCustomerCredit());
        return "OnlinePayment";
    }

    @PostMapping("/save")
    public String savePayment(
            @ModelAttribute OnlinePayment onlinePayment,
            Model model
    ) {
        if (onlinePayment.getCaptcha().equals(onlinePayment.getHiddenCaptcha()) && onlinePayment.getNow().plusMinutes(10).isAfter(LocalDateTime.now())) {
            Order byId = orderService.findById(onlinePayment.getOrderId());
            orderService.payForExperts(byId, "online");
            orderService.changeStatusTo(byId, OrderStatus.DONE);
            return "SuccessfullyPayment";
        } else {
            model.addAttribute("message", "Invalid Captcha");
            getCaptcha(onlinePayment);
            model.addAttribute("paymentData", onlinePayment);
        }
        return "OnlinePayment";
    }

    private void getCaptcha(OnlinePayment user) {
        Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
        user.setHiddenCaptcha(captcha.getAnswer());
        user.setCaptcha(""); // value entered by the User
        user.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));

    }
}
