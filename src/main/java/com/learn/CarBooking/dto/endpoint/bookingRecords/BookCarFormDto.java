package com.learn.CarBooking.dto.endpoint.bookingRecords;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCarFormDto {

  private Long customerPhoneNumber;
  private String customerName;
  private String customerEmail;
  private String pickupState;
  private String pickupCity;
  private String pickupLocalAddress;
  private String dropState;
  private String dropCity;
  private String dropLocalAddress;
  private String country;
  private String bookingType;
  private Integer distance;
  private Integer numberOfSUVcars;
  private Integer numberOfMinicars;
  private Date startDate;
  private Date endDate;
}
