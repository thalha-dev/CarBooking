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

import com.learn.CarBooking.entity.CarEntity;
import com.learn.CarBooking.repository.CarRepository;

@RestController
public class CarController {

  private final CarRepository carRepository;

  public CarController(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  @PostMapping("/cars/addNewCar")
  public ResponseEntity<CarEntity> createCar(@RequestBody CarEntity car) {
    CarEntity savedCar = carRepository.save(car);
    return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
  }

  @GetMapping("/cars/{carId}")
  public ResponseEntity<?> getCar(@PathVariable Long carId) {
    Optional<CarEntity> car = carRepository.findById(carId);
    if (car.isPresent()) {
      return new ResponseEntity<CarEntity>(car.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Car is not present", HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/cars/getAllCars")
  public ResponseEntity<List<CarEntity>> getAllCars() {
    List<CarEntity> cars = carRepository.findAll();
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

}
