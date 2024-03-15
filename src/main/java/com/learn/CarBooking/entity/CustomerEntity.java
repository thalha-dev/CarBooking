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
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(name = "customer_email_unique", columnNames = "customer_email"))
public class CustomerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id")
  private Long Id;

  @Column(name = "customer_name")
  private String customerName;

  @Column(name = "customer_email")
  private String customerEmail;

  @Column(name = "customer_phone_number")
  private Long customerPhoneNumber;

}
