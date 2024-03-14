package com.learn.CarBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.CarBooking.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
