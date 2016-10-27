package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;


import java.util.List;

public interface OrderRepository {

    Order find(long id);

    List<Order> findAllByCustomer(Customer customer);

    List<Order> findAll();

    Order save(Order order);
}
