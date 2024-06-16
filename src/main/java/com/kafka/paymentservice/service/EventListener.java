package com.kafka.paymentservice.service;

import blackfriday.protobuf.EdaMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventListener {

    @Autowired
    PaymentService paymentService;

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @KafkaListener(topics = "payment_request")
    public void consumePaymentRequest(byte[] message) throws Exception {
        var object = EdaMessage.PaymentRequestV1.parseFrom(message);

        log.info("[payment_request] consumed : {}", object);

        var payment = paymentService.processPayment(
                object.getUserId(),
                object.getOrderId(),
                object.getAmountKRW(),
                object.getPaymentMethodId()
        );

        var paymentResult = EdaMessage.PaymentResultV1.newBuilder()
                .setOrderId(payment.orderId)
                .setPaymentId(payment.id)
                .setPaymentStatus(payment.paymentStatus.toString())
                .build();

        kafkaTemplate.send("payment_result", paymentResult.toByteArray());
    }

}
