package com.learn.CarBooking.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learn.CarBooking.dto.endpoint.bookingRecords.BookCarFormDto;
import com.learn.CarBooking.dto.message.ResponseDto;
import com.learn.CarBooking.entity.BCCDMappingEntity;
import com.learn.CarBooking.entity.BookingRecordsEntity;
import com.learn.CarBooking.entity.CarEntity;
import com.learn.CarBooking.entity.CustomerEntity;
import com.learn.CarBooking.entity.DriverEntity;
import com.learn.CarBooking.entity.LocationEntity;
import com.learn.CarBooking.entity.PaymentEntity;
import com.learn.CarBooking.repository.BCCDMappingRepository;
import com.learn.CarBooking.repository.BookingRecordsRepository;
import com.learn.CarBooking.repository.CarRepository;
import com.learn.CarBooking.repository.CustomerRepository;
import com.learn.CarBooking.repository.DriverRepository;
import com.learn.CarBooking.repository.LocationRepository;
import com.learn.CarBooking.repository.PaymentRepository;
import com.learn.CarBooking.service.EmailService;

@RestController
public class BookingRecordsController {

  @Autowired
  private BookingRecordsRepository bookingRecordsRepository;

  @Autowired
  private DriverRepository driverRepository;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private BCCDMappingRepository bccdMappingRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private EmailService emailService;

  private static final Logger logger = LoggerFactory.getLogger(BookingRecordsController.class);

  @PostMapping("/bookingRecords/addNewBooking")
  public ResponseEntity<ResponseDto<?>> createBooking(@RequestBody BookCarFormDto bookingForm) {
    List<BookingRecordsEntity> bookingsInConflict = bookingRecordsRepository
        .findBookingsConflictingWithStartDate(bookingForm.getStartDate(), bookingForm.getEndDate());

    List<Long> occupiedCarIds = bookingsInConflict.stream()
        .flatMap(booking -> bccdMappingRepository.findBCCDMappingsByBookingId(booking.getId()).stream())
        .map(mapping -> mapping.getCarId())
        .collect(Collectors.toList());

    List<Long> occupiedDriverIds = bookingsInConflict.stream()
        .flatMap(booking -> bccdMappingRepository.findBCCDMappingsByBookingId(booking.getId()).stream())
        .map(mapping -> mapping.getDriverId())
        .filter(id -> id != null)
        .collect(Collectors.toList());

    List<CarEntity> availableCars = carRepository.findAll().stream()
        .filter(car -> !occupiedCarIds.contains(car.getId()))
        .collect(Collectors.toList());

    List<DriverEntity> availableDrivers = driverRepository.findAll().stream()
        .filter(driver -> !occupiedDriverIds.contains(driver.getId()))
        .collect(Collectors.toList());

    int occupiedSUV = 0;
    int occupiedMini = 0;
    int occupiedDrivers = 0;

    Integer totalDrivers = driverRepository.findTotalNumberOfDrivers();
    Integer totalSUVcars = carRepository.findTotalNumberOfSUVcars();
    Integer totalMinicars = carRepository.findTotalNumberOfMinicars();

    for (BookingRecordsEntity booking : bookingsInConflict) {
      occupiedSUV = occupiedSUV + booking.getNumberOfSUV();
      occupiedMini = occupiedMini + booking.getNumberOfMini();
      if (booking.getBookingType().equals("hire")) {
        occupiedDrivers = occupiedDrivers + booking.getNumberOfSUV() + booking.getNumberOfMini();
      }
    }

    int availableSUV = totalSUVcars - occupiedSUV;
    int availableMini = totalMinicars - occupiedMini;
    int availableDriversCount = totalDrivers - occupiedDrivers;

    if (bookingForm.getNumberOfSUVcars() > availableSUV && bookingForm.getNumberOfMinicars() > availableMini) {
      ResponseDto<String> send = new ResponseDto<>();
      send.setResponse(null);
      send.setError("Only " + availableSUV + " SUV cars and " + availableMini + " mini cars  are available on the date "
          + bookingForm.getStartDate());
      return new ResponseEntity<>(send, HttpStatus.NOT_FOUND);
    }

    if (bookingForm.getNumberOfSUVcars() > availableSUV) {
      ResponseDto<String> send = new ResponseDto<>();
      send.setResponse(null);
      send.setError("Only " + availableSUV + " SUV cars are available on the date " + bookingForm.getStartDate());
      return new ResponseEntity<>(send, HttpStatus.NOT_FOUND);
    }

    if (bookingForm.getNumberOfMinicars() > availableMini) {
      ResponseDto<String> send = new ResponseDto<>();
      send.setResponse(null);
      send.setError("Only " + availableMini + " mini cars are available on the date " + bookingForm.getStartDate());
      return new ResponseEntity<>(send, HttpStatus.NOT_FOUND);
    }

    if (bookingForm.getBookingType().equals("hire")
        && (bookingForm.getNumberOfSUVcars() + bookingForm.getNumberOfMinicars() > availableDriversCount)) {
      ResponseDto<String> send = new ResponseDto<>();
      send.setResponse(null);
      send.setError(
          "Only " + availableDriversCount + " drivers are available on the date " + bookingForm.getStartDate());
      return new ResponseEntity<>(send, HttpStatus.NOT_FOUND);
    }

    // If there are enough cars and drivers available, create the booking
    BookingRecordsEntity newBooking = new BookingRecordsEntity();
    newBooking.setJourneyStartDate(bookingForm.getStartDate());
    newBooking.setJourneyEndDate(bookingForm.getEndDate());
    newBooking.setBookingType(bookingForm.getBookingType());
    newBooking.setNumberOfSUV(bookingForm.getNumberOfSUVcars());
    newBooking.setNumberOfMini(bookingForm.getNumberOfMinicars());

    LocationEntity location = new LocationEntity();
    location.setCountry(bookingForm.getCountry());
    location.setPickupCity(bookingForm.getPickupCity());
    location.setPickupState(bookingForm.getPickupState());
    location.setPickupLocalAddress(bookingForm.getPickupLocalAddress());
    location.setDropCity(bookingForm.getDropCity());
    location.setDropState(bookingForm.getDropState());
    location.setDropLocalAddress(bookingForm.getDropLocalAddress());

    BookingRecordsEntity savedBooking = bookingRecordsRepository.save(newBooking);

    location.setBookingRecordsId(savedBooking.getId());
    locationRepository.save(location);

    savedBooking.setLocation(location);

    Optional<CustomerEntity> customerOptional = customerRepository.findByCustomerEmail(bookingForm.getCustomerEmail());
    CustomerEntity customer;

    if (customerOptional.isPresent()) {
      customer = customerOptional.get();
    } else {
      customer = new CustomerEntity();
      customer.setCustomerName(bookingForm.getCustomerName());
      customer.setCustomerEmail(bookingForm.getCustomerEmail());
      customer.setCustomerPhoneNumber(bookingForm.getCustomerPhoneNumber());
      customer = customerRepository.save(customer);
    }

    Integer neededSUV = bookingForm.getNumberOfSUVcars();
    Integer neededMini = bookingForm.getNumberOfMinicars();
    Integer totalCharge = 0;

    for (CarEntity car : availableCars) {
      if ((neededSUV > 0 && car.getCarType().equals("SUV"))) {
        totalCharge = totalCharge + (car.getPricePerKm() * bookingForm.getDistance());

        DriverEntity driver = null;
        if (!availableDrivers.isEmpty()) {
          driver = availableDrivers.remove(0);
        }

        BCCDMappingEntity newMap = new BCCDMappingEntity();
        newMap.setBookingRecordsId(savedBooking.getId());
        newMap.setCarId(car.getId());
        if (driver != null && bookingForm.getBookingType().equals("hire")) {
          newMap.setDriverId(driver.getId());
        }
        newMap.setCustomerId(customer.getId());

        bccdMappingRepository.save(newMap);

        neededSUV--;
        continue;
      }

      if ((neededMini > 0 && car.getCarType().equals("mini"))) {
        totalCharge = totalCharge + (car.getPricePerKm() * bookingForm.getDistance());

        DriverEntity driver = null;
        if (!availableDrivers.isEmpty()) {
          driver = availableDrivers.remove(0);
        }

        BCCDMappingEntity newMap = new BCCDMappingEntity();
        newMap.setBookingRecordsId(savedBooking.getId());
        newMap.setCarId(car.getId());
        if (driver != null && bookingForm.getBookingType().equals("hire")) {
          newMap.setDriverId(driver.getId());
        }
        newMap.setCustomerId(customer.getId());

        bccdMappingRepository.save(newMap);
        neededMini--;
        continue;
      }
    }

    PaymentEntity payment = new PaymentEntity();
    payment.setBookingRecordsId(savedBooking.getId());
    payment.setTotalCharge(totalCharge);
    payment.setPaymentCurrentStatus("pending");

    payment = paymentRepository.save(payment);

    savedBooking.setPayment(payment);

    savedBooking = bookingRecordsRepository.save(savedBooking);

    ResponseDto<BookingRecordsEntity> send = new ResponseDto<>();
    send.setResponse(savedBooking);
    send.setError(null);

    String emailStatus = emailService.sendMail(null, customer.getCustomerEmail(), null, "Booking Confirmation",
        "<h1 style='background: black;color: white;padding: 1em;'>Your booking has been confirmed!</h1>" +
            "<p style='background: lightgreen;padding: 1em;'>You booked "
            + (bookingForm.getNumberOfSUVcars() + bookingForm.getNumberOfMinicars()) + " cars for the date "
            + bookingForm.getStartDate() + " </p>");

    logger.info("Email Status: {}", emailStatus);

    return new ResponseEntity<>(send, HttpStatus.CREATED);
  }

  @GetMapping("/bookingRecords/{bookingId}")
  public ResponseEntity<?> getBooking(@PathVariable Long bookingId) {
    Optional<BookingRecordsEntity> booking = bookingRecordsRepository.findById(bookingId);
    if (booking.isPresent()) {
      return new ResponseEntity<>(booking.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Booking Not Found", HttpStatus.NOT_FOUND);
    }
  }
}
