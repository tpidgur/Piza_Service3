package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;


public interface OrderService {
    Order placeNewOrder(Customer customer, Long... pizzasID);

    Order findOrderById(Long orderId);

    void addPizzasToExistingOrder(Long orderId, Long... pizzaId);

    void removePizzaFromExistingOrder(Long orderId, Long pizzaId);

    void setCancelStatus(Long orderId);

    void setDoneStatus(Long orderId);

    void setInProgressStatus(Long orderId);
}
