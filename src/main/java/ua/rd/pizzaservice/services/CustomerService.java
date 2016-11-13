package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer find(Long id);

    List<Customer> findAllByName(String name);

    List<Customer> findAll();

    Customer save(Customer customer);

}
