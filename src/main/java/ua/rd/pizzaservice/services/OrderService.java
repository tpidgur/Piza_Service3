package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order placeNewOrder(Customer customer, long... pizzasID);
    Order findOrderById(Long orderId);

}
