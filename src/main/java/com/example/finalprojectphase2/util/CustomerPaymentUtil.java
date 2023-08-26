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

    public ResponseEntity<Object> payWithCredit(Order order) {

        Float customerCredit = order.getCustomer().getCredit();
        if (customerCredit < order.getFinalOffer().getOfferedPrice())
            return ResponseEntity.internalServerError().body("  وجه اعتبار شما کمتر ازمبلغ پرداختی میباشد لطفا ابتدا اعتبار خود را افزایش دهید");

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
        return ResponseEntity.accepted().body("پرداخت با موفقیت انجام شد");
    }

    public ResponseEntity<Object> payOnline(Order order) {

        Float customerCredit = order.getCustomer().getCredit();
        if (customerCredit < order.getFinalOffer().getOfferedPrice())
            return ResponseEntity.internalServerError().body("  وجه اعتبار شما کمتر ازمبلغ پرداختی میباشد لطفا ابتدا اعتبار خود را افزایش دهید");

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
        return ResponseEntity.accepted().body("پرداخت با موفقیت انجام شد");
    }
}
