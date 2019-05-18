package com.joshuamonk.phoneapi.controller;

import com.joshuamonk.phoneapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/numbers", method = RequestMethod.GET)
    public Collection<String> getAllNumbers() {
        return this.customerService.getAllNumbers();
    }
}
