package com.learn.CarBooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "driver", uniqueConstraints = @UniqueConstraint(name = "driver_phone_number_unique", columnNames = "driver_phone_number"))
public class DriverEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "driver_id")
  private Long Id;

  @Column(name = "driver_name")
  private String driverName;

  @Column(name = "driver_phone_number")
  private Long driverPhoneNumber;

  // inDuty or notInDuty
  @Column(name = "driver_current_status")
  private String driverCurrentStatus;

  @Column(name = "driver_avg_rating")
  private Double avgRating;
}
