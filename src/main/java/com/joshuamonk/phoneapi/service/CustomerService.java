package com.joshuamonk.phoneapi.service;

import com.joshuamonk.phoneapi.exception.CustomerNotFoundException;
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

    /**
     * Gets all of the phone numbers currently associated with a customer in the system
     * @return  List<String> containing all numbers in the system
     */
    public List<String> getAllNumbers() {
        return this.customerRepo.getAllNumbers();
    }

    /**
     * Get all phone numbers associated with a given customer
     * @param id Id of customer to get associated numbers for
     * @return List<String> containing the customer numbers
     * @throws CustomerNotFoundException When customer could not be found in the system
     */
    public List<String> getCustomerNumbers(long id) throws CustomerNotFoundException {
        return this.customerRepo.getCustomerNumbers(id).orElseThrow(() -> new CustomerNotFoundException("Customer with id:" + id + " does not exist"));
    }
}
