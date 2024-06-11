package com.kafka.paymentservice.entity;

import com.kafka.paymentservice.enums.PaymentMethodType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

/**
 * @title: 결제수단
 */
@Entity
@Table(indexes = {@Index(name = "idx_userId", columnList = "userId")})
@NoArgsConstructor
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long userId;

    public PaymentMethodType paymentMethodType;
    public String creditCardNumber;

    public PaymentMethod(Long userId, PaymentMethodType paymentMethodType, String creditCardNumber) {
        this.userId = userId;
        this.paymentMethodType = paymentMethodType;
        this.creditCardNumber = creditCardNumber;
    }
}
