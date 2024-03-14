package com.learn.CarBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.CarBooking.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

}
