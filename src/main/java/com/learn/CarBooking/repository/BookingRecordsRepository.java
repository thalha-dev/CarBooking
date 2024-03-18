package com.learn.CarBooking.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.learn.CarBooking.entity.BookingRecordsEntity;

public interface BookingRecordsRepository extends JpaRepository<BookingRecordsEntity, Long> {

  @Query("SELECT b from BookingRecordsEntity b where (b.journeyEndDate >= :startDate and b.journeyStartDate <= :endDate)")
  List<BookingRecordsEntity> findBookingsConflictingWithStartDate(@Param("startDate") Date startDate,
      @Param("endDate") Date endDate);

  @Query("SELECT b from BookingRecordsEntity b where b.journeyStartDate <= :currentDate and b.journeyEndDate >= :currentDate")
  List<BookingRecordsEntity> findBookingsInTravel(@Param("currentDate") Date currentDate);

  @Query("SELECT b from BookingRecordsEntity b where b.journeyEndDate > :startDate and b.journeyStartDate <= :endDate")
  List<BookingRecordsEntity> findBookingsNotInTravel(@Param("startDate") Date startDate,
      @Param("endDate") Date endDate);

}
