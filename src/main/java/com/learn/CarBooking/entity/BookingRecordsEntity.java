package com.learn.CarBooking.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "booking_records")
public class BookingRecordsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "booking_records_id")
  private Long Id;

  @Column(name = "journey_start_date")
  private Date journeyStartDate;

  @Column(name = "journey_end_date")
  private Date journeyEndDate;

  // hire or rental
  @Column(name = "booking_type")
  private String bookingType;

  @Column(name = "number_of_mini")
  private Integer numberOfMini;

  @Column(name = "number_of_suv")
  private Integer numberOfSUV;

  @OneToOne
  @JoinColumn(name = "booking_records_id")
  private PaymentEntity payment;

  @OneToOne
  @JoinColumn(name = "booking_records_id")
  private LocationEntity location;

}
