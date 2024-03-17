package com.learn.CarBooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learn.CarBooking.entity.DriverEntity;
import com.learn.CarBooking.repository.DriverRepository;

@RestController
public class DriverController {

  private final DriverRepository driverRepository;

  public DriverController(DriverRepository driverRepository) {
    this.driverRepository = driverRepository;
  }

  @PostMapping("/drivers/addNewDriver")
  public ResponseEntity<DriverEntity> createDriver(@RequestBody DriverEntity driver) {
    DriverEntity savedDriver = driverRepository.save(driver);
    return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
  }

  @GetMapping("/drivers/{driverId}")
  public ResponseEntity<DriverEntity> getDriver(@PathVariable Long driverId) {
    Optional<DriverEntity> driver = driverRepository.findById(driverId);
    if (driver.isPresent()) {
      return new ResponseEntity<>(driver.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/drivers/getAllDrivers")
  public ResponseEntity<List<DriverEntity>> getAllDrivers() {
    List<DriverEntity> drivers = driverRepository.findAll();
    return new ResponseEntity<>(drivers, HttpStatus.OK);
  }
}
