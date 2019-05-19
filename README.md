# PhoneApi
Repo for phone number management REST API

## Development and Build Info
 - Developed with Java 8, Spring Boot 2.1.5 and Maven 3.6.0
 - Developed on Ubuntu 18.10

## API Design
Below is a description of the implemented endpoints for the API, with example uses.

### API description:
Retrieve a list of all phone numbers currently stored in the system and associated with a customer.

### API endpoint:
GET localhost:8080/api/v1/customers/numbers

### API description:
Retrieve a list of phone numbers for one particular customer

###  API endpoint:
GET localhost:8080/api/v1/customers/{id}/numbers

{id}: id of customer you wish to make request for, e.g. localhost:8080/api/v1/customers/1/numbers

### API description:
Activate a phone number for a given customer

### API endpoint:
PATCH localhost:8080/api/v1/customers/{id}/numbers

{id}: id of customer you wish to make request for, e.g. localhost:8080/api/v1/customers/1/numbers

### API Request Body:
JSON containing number wishing to be activated, e.g. {"number": "01234567890"}


