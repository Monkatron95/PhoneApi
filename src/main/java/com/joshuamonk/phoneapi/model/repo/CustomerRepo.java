package com.joshuamonk.phoneapi.model.repo;

import com.joshuamonk.phoneapi.model.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Repository for handling phone numbers in system
 */
@Repository
public class CustomerRepo {

    // Dummy data to be used by system, as no database is implemented
    private static HashMap<Long, Customer> customers = new HashMap<Long, Customer>() {
        {
            put(1L, new Customer(1L, "Bob", "Bobby", "1 Bob Lane",
                    "07876765434", Collections.singletonList("07876765434")));
            put(2L, new Customer(1L, "Fred", "Bobby", "2 Bob Lane",
                    "07876765435", Collections.singletonList("07876765435")));
            put(3L, new Customer(1L, "Ted", "Bobby", "3 Bob Lane",
                    "07876765436", Arrays.asList("07876765436", "07876765437")));
            put(4L, new Customer(1L, "Pete", "Bobby", "4 Bob Lane",
                    "078767654348", Collections.singletonList("07876765438")));
            put(5L, new Customer(1L, "James", "Bobby", "5 Bob Lane",
                    "07876765439", Collections.singletonList("07876765439")));
            put(6L, new Customer(1L, "Philip", "Bobby", "6 Bob Lane",
                    "07876765410", Collections.singletonList("07876765410")));
            put(7L, new Customer(1L, "Joe", "Bobby", "7 Bob Lane",
                    "07876765409", Collections.singletonList("07876765409")));
            put(8L, new Customer(1L, "Ben", "Bobby", "8 Bob Lane",
                    "07876765408", Arrays.asList("07876765408", "07876765407")));
            put(9L, new Customer(1L, "Adam", "Bobby", "9 Bob Lane",
                    "07876765406", Collections.singletonList("07876765406")));

        }
    };

    /**
     * Get all numbers currently stored in the system
     *
     * @return List<String> containing all numbers currently in the system
     */
    public List<String> getAllNumbers() {
        List<String> numbersList = new ArrayList<>();

        // Go through all customers in system and collect all of their phone numbers
        for (Customer customer : customers.values()) {
            numbersList.addAll(customer.getAssociatedNumbers());
        }

        return numbersList;
    }

    /**
     * Gets all the numbers currently associated with a customer in the system
     *
     * @param id Id of the customer to find numbers for
     * @return Optional containing List<String> of numbers
     */
    public Optional<List<String>> getCustomerNumbers(long id) {
        if (customers.containsKey(id)) {
            List<String> numbers = customers.get(id).getAssociatedNumbers();
            return Optional.of(numbers);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets all the numbers currently associated with a customer in the system
     *
     * @param id Id of the customer to add number for
     * @return Optional containing updated Customer object
     */
    public Optional<Customer> addCustomerPhoneNumber(long id, String number) {
        if (customers.containsKey(id)) {

            // Add number to customers associated numbers, activating it in system
            ArrayList<String> numbers = new ArrayList<>(customers.get(id).getAssociatedNumbers());
            numbers.add(number);
            customers.get(id).setAssociatedNumbers(numbers);

            // Return updated customer object
            return Optional.of(customers.get(id));
        } else {
            return Optional.empty();
        }
    }
}
