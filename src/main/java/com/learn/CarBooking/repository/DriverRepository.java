package com.learn.CarBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.CarBooking.entity.DriverEntity;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

}
