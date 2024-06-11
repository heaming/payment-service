package com.kafka.paymentservice.dto;

import com.kafka.paymentservice.enums.PaymentMethodType;

public class PaymentMethodDto {
    public Long userId;
    public PaymentMethodType paymentMethodType;
    public String creditCardNumber;
}
