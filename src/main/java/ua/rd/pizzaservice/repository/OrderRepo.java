package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Order;


import java.util.List;

public interface OrderRepo {
    public List<Order> getOrders();
    Order save (Order order);
}
