package com.joshuamonk.phoneapi.controller;

import com.joshuamonk.phoneapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Rest controller for defining endpoints of API which are exposed to the user
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Endpoint for getting all numbers currently stored in the system
     * @return ResponseEntity containing a collection of numbers in string format
     */
    @RequestMapping(value = "/numbers", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getAllNumbers() {
        return ResponseEntity.ok(this.customerService.getAllNumbers());
    }

    /**
     * Endpoint for getting all numbers currently associated with a customer
     * @param id Id of customer to return numbers for
     * @return ResponseEntity containing a collection of numbers in string format
     */
    @RequestMapping(value = "{id}/numbers", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getCustomerNumbersById(@PathVariable long id) {
        return ResponseEntity.ok(this.customerService.getCustomerNumbers(id));
    }
}
