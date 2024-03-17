package com.learn.CarBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learn.CarBooking.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Long> {

  @Query("SELECT count(c) from CarEntity c where c.carType = 'SUV'")
  Integer findTotalNumberOfSUVcars();

  @Query("SELECT count(c) from CarEntity c where c.carType = 'mini'")
  Integer findTotalNumberOfMinicars();

}
