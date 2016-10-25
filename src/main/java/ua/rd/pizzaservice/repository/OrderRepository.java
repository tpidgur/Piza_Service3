package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;


import java.util.List;

public interface OrderRepository {

    Order save(Order order);

    Order find(long id);

    List<Order> findByCustomer(Customer customer);

    List<Order> findAll();
}
