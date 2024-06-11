package com.kafka.paymentservice.paymentGate;

import org.springframework.stereotype.Service;

@Service
public class EasyCreditCardPaymentAdapter implements CreditCardPaymentAdapter{
    @Override
    public Long processCreditPayment(Long amountKRW, String creditCardNumber) {
        // actual process with external system
        return Math.round(Math.random() * 10000000);
    }
}
