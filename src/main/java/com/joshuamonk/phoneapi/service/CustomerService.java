package com.joshuamonk.phoneapi.service;

import com.joshuamonk.phoneapi.exception.CustomerNotFoundException;
import com.joshuamonk.phoneapi.exception.InvalidPhoneNumberExeception;
import com.joshuamonk.phoneapi.exception.PhoneNumberInUseException;
import com.joshuamonk.phoneapi.model.entity.Customer;
import com.joshuamonk.phoneapi.model.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling phone numbers in the system
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    // Regular expression for validating phone number given to API
    private static final String PHONE_NUMBER_REGEX = "^(?:\\(\\+?\\d{2,3}\\)|\\+?\\d{2,3})\\s?(?:\\d{4}[\\s*.-]?" +
            "\\d{4}|\\d{3}[\\s*.-]?\\d{3}|\\d{2}([\\s*.-]?)\\d{2}\\1?\\d{2}(?:\\1?\\d{2})?)(?:\\1?\\d{2})?$";

    /**
     * Gets all of the phone numbers currently associated with a customer in the system
     *
     * @return List<String> containing all numbers in the system
     */
    public List<String> getAllNumbers() {
        return this.customerRepo.getAllNumbers();
    }

    /**
     * Get all phone numbers associated with a given customer
     *
     * @param id Id of customer to get associated numbers for
     * @return List<String> containing the customer numbers
     * @throws CustomerNotFoundException When customer could not be found in the system
     */
    public List<String> getCustomerNumbers(long id) throws CustomerNotFoundException {
        return this.customerRepo.getCustomerNumbers(id).orElseThrow(() ->
                new CustomerNotFoundException("Customer with id:" + id + " does not exist"));
    }

    /**
     * Activates a number for a given customer
     *
     * @param id     Id of customer to activate number for
     * @param number phone number to activate
     * @return Customer object updated to contain the activated phone number
     * @throws CustomerNotFoundException    when the id given does not match a customer in the system
     * @throws InvalidPhoneNumberExeception when the given number is not a valid phone number
     * @throws PhoneNumberInUseException    when the given number is already activated for another customer
     */
    public Customer activateCustomerPhoneNumber(long id, String number) throws CustomerNotFoundException,
            InvalidPhoneNumberExeception, PhoneNumberInUseException {

        // If the phone number is not valid, throw exception and do not activate
        if (!number.matches(PHONE_NUMBER_REGEX)) {
            throw new InvalidPhoneNumberExeception("Phone number provided is not valid, could not be activated");
        }

        List<String> currentNumbers = this.customerRepo.getAllNumbers();

        // If the phone number is already in use, throw exception and do not activate
        if (currentNumbers.contains(number)) {
            throw new PhoneNumberInUseException("This phone number is already in use");
        }

        // Activate phone number and return updated object
        return this.customerRepo.addCustomerPhoneNumber(id, number).orElseThrow(() ->
                new CustomerNotFoundException("Customer with id:" + id + " does not exist"));
    }
}
