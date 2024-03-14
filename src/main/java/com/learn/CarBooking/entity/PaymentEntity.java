package com.learn.CarBooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "payment")
public class PaymentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id")
  private Long Id;

  @Column(name = "booking_records_id")
  private Long bookingRecordsId;

  // pending or paid
  @Column(name = "payment_current_status")
  private String paymentCurrentStatus;

  @Column(name = "total_charge")
  private Integer totalCharge;

}
