package com.learn.CarBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.CarBooking.entity.BookingRecordsEntity;

public interface BookingRecordsRepository extends JpaRepository<BookingRecordsEntity, Long> {

}
