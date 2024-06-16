package com.kafka.paymentservice.controller;

import com.kafka.paymentservice.dto.PaymentMethodDto;
import com.kafka.paymentservice.entity.Payment;
import com.kafka.paymentservice.entity.PaymentMethod;
import com.kafka.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/methods")
    public PaymentMethod registerPaymentMethod(@RequestBody PaymentMethodDto request) {
        return paymentService.registerPaymentMethod(request.userId, request.paymentMethodType, request.creditCardNumber);
    }

    @GetMapping("/users/{userId}/first-method")
    public PaymentMethod getPaymentMethod(@PathVariable Long userId) {
        return paymentService.getPaymentMethod(userId);
    }

    @GetMapping("/payments/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }
}
