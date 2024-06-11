package com.kafka.paymentservice.paymentGate;

public interface CreditCardPaymentAdapter {
    Long processCreditPayment(Long amountKRW, String creditCardNumber);
}
