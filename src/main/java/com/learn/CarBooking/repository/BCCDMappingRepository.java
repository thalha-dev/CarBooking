package com.learn.CarBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.learn.CarBooking.entity.BCCDMappingEntity;

public interface BCCDMappingRepository extends JpaRepository<BCCDMappingEntity, Long> {

  @Query("SELECT b from BCCDMappingEntity b where b.bookingRecordsId = :bookingId")
  List<BCCDMappingEntity> findBCCDMappingsByBookingId(@Param("bookingId") Long bookingId);
}
