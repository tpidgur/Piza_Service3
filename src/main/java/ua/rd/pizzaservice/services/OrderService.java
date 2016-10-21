package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.PizzaOrder;

public interface OrderService {
    PizzaOrder placeNewOrder(Customer customer, Long... pizzasID);
}
