package com.learn.CarBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learn.CarBooking.entity.DriverEntity;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

  @Query("SELECT count(d) from DriverEntity d")
  Integer findTotalNumberOfDrivers();

}
