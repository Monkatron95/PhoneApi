package com.joshuamonk.phoneapi.service;

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
}
