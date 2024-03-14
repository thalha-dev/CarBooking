package com.learn.CarBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.CarBooking.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Long> {

}
