package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();

    List<Customer> findAllByName(String name);

    Customer find(Long id);

    Customer save(Customer customer);

    void delete();
}
