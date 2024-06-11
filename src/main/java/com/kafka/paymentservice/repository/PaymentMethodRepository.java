package com.kafka.paymentservice.repository;

import com.kafka.paymentservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository <PaymentMethod, Long>{
    List<PaymentMethod> findByUserId(Long userId);

}
