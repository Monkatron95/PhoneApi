package com.joshuamonk.phoneapi.controller;

import com.joshuamonk.phoneapi.model.entity.Customer;
import com.joshuamonk.phoneapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

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
     *
     * @return ResponseEntity containing a collection of numbers in string format
     */
    @RequestMapping(value = "/numbers", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getAllNumbers() {
        return ResponseEntity.ok(this.customerService.getAllNumbers());
    }

    /**
     * Endpoint for getting all numbers currently associated with a customer
     *
     * @param id Id of customer to return numbers for
     * @return ResponseEntity containing a collection of numbers in string format
     */
    @RequestMapping(value = "{id}/numbers", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getCustomerNumbersById(@PathVariable long id) {
        return ResponseEntity.ok(this.customerService.getCustomerNumbers(id));
    }

    /**
     * Endpoint for activating a phone number for a customer
     *
     * @param body Request in JSON format, requiring a "number" parameter
     * @return ResponseEntity with corresponding response code
     */
    @RequestMapping(value = "{id}/numbers", method = RequestMethod.PATCH)
    public ResponseEntity<Customer> activateCustomerPhoneNumber(@PathVariable long id,
                                                                @RequestBody Map<String, String> body) {
        if (!body.containsKey("number")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.customerService.activateCustomerPhoneNumber(id, body.get("number")));
        }
    }
}
