package com.joshuamonk.phoneapi.controller;

import com.joshuamonk.phoneapi.model.entity.Customer;
import com.joshuamonk.phoneapi.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test cases for CustomerController Class
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @Spy
    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private HashMap<Long, Customer> customers;

    private List<String> numbers;

    @Before
    public void setup() {
        this.numbers = new ArrayList<>();
        this.customers = new HashMap<Long, Customer>() {
            {
                put(1L, new Customer(1L, "Bob", "Bobby", "1 Bob Lane", "07876765434", Collections.singletonList("07876765434")));
                put(2L, new Customer(1L, "Fred", "Bobby", "2 Bob Lane", "07876765435", Collections.singletonList("07876765435")));
                put(3L, new Customer(1L, "Ted", "Bobby", "3 Bob Lane", "07876765436", Arrays.asList("07876765436", "07876765437")));
                put(4L, new Customer(1L, "Pete", "Bobby", "4 Bob Lane", "078767654348", Collections.singletonList("07876765438")));
                put(5L, new Customer(1L, "James", "Bobby", "5 Bob Lane", "07876765439", Collections.singletonList("07876765439")));
                put(6L, new Customer(1L, "Philip", "Bobby", "6 Bob Lane", "07876765410", Collections.singletonList("07876765410")));
                put(7L, new Customer(1L, "Joe", "Bobby", "7 Bob Lane", "07876765409", Collections.singletonList("07876765409")));
                put(8L, new Customer(1L, "Ben", "Bobby", "8 Bob Lane", "07876765408", Arrays.asList("07876765408", "07876765407")));
                put(9L, new Customer(1L, "Adam", "Bobby", "9 Bob Lane", "07876765406", Collections.singletonList("07876765406")));
            }
        };

        // Go through all customers in system and collect all of their phone numbers
        for (Customer customer: customers.values()) {
            numbers.addAll(customer.getAssociatedNumbers());
        }
    }

    @Test
    public void getAllNumbers() {
        when(customerService.getAllNumbers()).thenReturn(numbers);

        Collection<String> result = this.customerController.getAllNumbers().getBody();

        assertThat(result).containsExactlyInAnyOrder(numbers.toArray(new String[0]));
    }
}
