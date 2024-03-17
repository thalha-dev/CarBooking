# Book Car

## details need to be passed in json

- customer name
- customer email
- picup location
  - city
  - state
  - local address
- drop location
  - city
  - state
  - local address
- country
- distance
- number of SUV
- number of mini
- journey start date
- journey end date

## cases

### check enough drivers and asked cars types available on journey start date

**Algorithm**

- get all bookings where enddate is on or after the new customer journey start date
- create variables occupiedSUV, occupiedMini, occupiedDrivers
- loop through the bookings and calcualte number of occupied SUVs, minis and Drivers.
- get total number of drivers in company
- get total number of SUV cars in company
- get total number of min cars in company
- calculate the differences
- proceed if ok, other wise give error message.
