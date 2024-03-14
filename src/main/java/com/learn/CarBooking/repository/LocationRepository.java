package com.learn.CarBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.CarBooking.entity.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

}
