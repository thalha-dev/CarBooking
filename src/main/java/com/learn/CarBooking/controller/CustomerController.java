package com.learn.CarBooking.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learn.CarBooking.entity.CustomerEntity;
import com.learn.CarBooking.repository.CustomerRepository;

@RestController
public class CustomerController {

  private final CustomerRepository customerRepository;

  public CustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @PostMapping("/customers")
  public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer) {
    CustomerEntity savedCustomer = customerRepository.save(customer);
    return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
  }

  @GetMapping("/customers/{customerId}")
  public ResponseEntity<CustomerEntity> getCustomer(@PathVariable Long customerId) {
    Optional<CustomerEntity> customer = customerRepository.findById(customerId);
    if (customer.isPresent()) {
      return new ResponseEntity<>(customer.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
