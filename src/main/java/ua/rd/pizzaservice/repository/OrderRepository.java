package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.PizzaOrder;


import java.util.List;

public interface OrderRepository {
    List<PizzaOrder> getOrders();

    PizzaOrder save(PizzaOrder order);

    PizzaOrder getOrder(long orderId);
}
