package com.joshuamonk.phoneapi.controller;

import com.joshuamonk.phoneapi.exception.CustomerNotFoundException;
import com.joshuamonk.phoneapi.exception.InvalidPhoneNumberExeception;
import com.joshuamonk.phoneapi.exception.PhoneNumberInUseException;
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
                put(1L, new Customer(1L, "Bob", "Bobby", "1 Bob Lane",
                        "07876765434", Collections.singletonList("07876765434")));
                put(2L, new Customer(2L, "Fred", "Bobby", "2 Bob Lane",
                        "07876765435", Collections.singletonList("07876765435")));
                put(3L, new Customer(3L, "Ted", "Bobby", "3 Bob Lane",
                        "07876765436", Arrays.asList("07876765436", "07876765437")));
                put(4L, new Customer(4L, "Pete", "Bobby", "4 Bob Lane",
                        "078767654348", Collections.singletonList("07876765438")));
                put(5L, new Customer(5L, "James", "Bobby", "5 Bob Lane",
                        "07876765439", Collections.singletonList("07876765439")));
                put(6L, new Customer(6L, "Philip", "Bobby", "6 Bob Lane",
                        "07876765410", Collections.singletonList("07876765410")));
                put(7L, new Customer(7L, "Joe", "Bobby", "7 Bob Lane",
                        "07876765409", Collections.singletonList("07876765409")));
                put(8L, new Customer(8L, "Ben", "Bobby", "8 Bob Lane",
                        "07876765408", Arrays.asList("07876765408", "07876765407")));
                put(9L, new Customer(9L, "Adam", "Bobby", "9 Bob Lane",
                        "07876765406", Collections.singletonList("07876765406")));
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


    @Test
    public void getCustomerNumbersById() {
        when(customerService.getCustomerNumbers(1L)).thenReturn(customers.get(1L).getAssociatedNumbers());

        Collection<String> result = this.customerController.getCustomerNumbersById(1L).getBody();

        assertThat(result).containsExactlyInAnyOrder(customers.get(1L).getAssociatedNumbers().toArray(new String[0]));
    }

    @Test(expected = CustomerNotFoundException.class)
    public void getCustomerNumbersByIdFailed() {
        when(customerService.getCustomerNumbers(1L))
                .thenThrow(new CustomerNotFoundException("Customer with id: 1 does not exist"));

        Collection<String> result = this.customerController.getCustomerNumbersById(1L).getBody();
    }

    @Test
    public void activateCustomerPhoneNumber() {
        when(customerService.activateCustomerPhoneNumber(1L, "01234567890"))
                .thenReturn(new Customer(1L, "Ben", "Bobby", "8 Bob Lane",
                        "07876765408", Arrays.asList("07876765408", "01234567890")));
        Customer result = this.customerService.activateCustomerPhoneNumber(1L, "01234567890");

        assertThat(result.getAssociatedNumbers()).contains("01234567890");
    }

    @Test(expected = InvalidPhoneNumberExeception.class)
    public void activateCustomerPhoneNumberInvalidPhoneNumber() {
        when(customerService.activateCustomerPhoneNumber(1L, ""))
                .thenThrow(new InvalidPhoneNumberExeception("Phone number is not valid"));

        Map<String, String> request = new HashMap<>();
        request.put("number", "");

        Customer result = this.customerController.activateCustomerPhoneNumber(1L, request).getBody();
    }

    @Test(expected = PhoneNumberInUseException.class)
    public void activateCustomerPhoneNumberNumberInUse() {
        when(customerService.activateCustomerPhoneNumber(1L, "01234567890"))
                .thenThrow(new PhoneNumberInUseException("Phone number is already in use"));

        Map<String, String> request = new HashMap<>();
        request.put("number", "01234567890");

        Customer result = this.customerController.activateCustomerPhoneNumber(1L, request).getBody();
    }

    @Test(expected = CustomerNotFoundException.class)
    public void activateCustomerPhoneNumberCustomerNotFound() {
        when(customerService.activateCustomerPhoneNumber(12L, "01234567890"))
                .thenThrow(new CustomerNotFoundException("Customer with Id:" + 12 + " could not be found"));

        Map<String, String> request = new HashMap<>();
        request.put("number", "01234567890");

        Customer result = this.customerController.activateCustomerPhoneNumber(12L, request).getBody();
    }
}
