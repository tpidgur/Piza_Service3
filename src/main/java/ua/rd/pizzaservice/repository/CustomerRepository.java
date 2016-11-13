package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer find(Long id);

    List<Customer> findAllByName(String name);

    List<Customer> findAll();

    void updateName(String newName, Long id);

    Customer save(Customer customer);

    void delete();
}
