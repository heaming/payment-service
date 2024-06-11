package com.kafka.paymentservice.service;

import com.kafka.paymentservice.entity.Payment;
import com.kafka.paymentservice.entity.PaymentMethod;
import com.kafka.paymentservice.enums.PaymentMethodType;
import com.kafka.paymentservice.enums.PaymentStatus;
import com.kafka.paymentservice.paymentGate.CreditCardPaymentAdapter;
import com.kafka.paymentservice.repository.PaymentMethodRepository;
import com.kafka.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CreditCardPaymentAdapter creditCardPaymentAdapter;

    public PaymentMethod registerPaymentMethod(Long userId,
                                               PaymentMethodType paymentMethodType,
                                               String creditCardNumber) {
        var paymentMethod = new PaymentMethod(userId, paymentMethodType, creditCardNumber);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * @note  어뎁터패턴
     * @param userId
     * @param orderId
     * @param amountKRW
     * @param paymentMethodId
     * @return
     * @throws Exception
     */
    public Payment processPayment(Long userId,
                                  Long orderId,
                                  Long amountKRW,
                                  Long paymentMethodId) throws Exception {
        var paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow();

        if(paymentMethod.paymentMethodType != PaymentMethodType.CREDIT_CARD) {
            throw new Exception("Unsupported payment method type");
        }

        // adapter pattern
        // 실제 결제 진행
        var refCode = creditCardPaymentAdapter.processCreditPayment(amountKRW, paymentMethod.creditCardNumber);
        var payment = new Payment(userId,
                orderId,
                amountKRW,
                paymentMethod.paymentMethodType,
                paymentMethod.creditCardNumber,
                PaymentStatus.COMPLETED,
                refCode);

        return paymentRepository.save(payment);
    }

    public PaymentMethod getPaymentMethod(Long userId) {
        return paymentMethodRepository.findByUserId(userId).stream().findFirst().orElseThrow();
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId).stream().findFirst().orElseThrow();
    }
}
