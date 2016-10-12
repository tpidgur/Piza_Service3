package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Order;


import java.util.List;

public interface OrderRepository {
    List<Order> getOrders();

    Order save(Order order);

    Order getOrder(long orderId);
}
