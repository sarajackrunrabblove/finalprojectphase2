package com.example.finalprojectphase2.util;

import com.example.finalprojectphase2.model.Order;
import com.example.finalprojectphase2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerPaymentUtil {
    private final UserRepository userRepository;

    public void payWithCredit(Order order) {

        Float customerCredit = order.getCustomer().getCredit();
        if (customerCredit < order.getFinalOffer().getOfferedPrice()) {
            ResponseEntity.internalServerError().body("  وجه اعتبار شما کمتر ازمبلغ پرداختی میباشد لطفا ابتدا اعتبار خود را افزایش دهید");
            return;
        }

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
                    user.setCredit(expertNewCredit + user.getCredit());
                    userRepository.save(user);
                }
        );
        ResponseEntity.accepted().body("پرداخت با موفقیت انجام شد");
    }

    public void payOnline(Order order) {


        userRepository.findByUserName(order.getFinalOffer().getExpert().getUserName()).ifPresent(
                user -> {
                    float expertNewCredit = order.getFinalOffer().getOfferedPrice() * 7 / 10;
                    user.setCredit(expertNewCredit + user.getCredit());
                    userRepository.save(user);
                }
        );
        ResponseEntity.accepted().body("پرداخت با موفقیت انجام شد");
    }
}
