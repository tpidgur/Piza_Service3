package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Order;

import java.util.LinkedList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepo {
    private List<Order> orders = new LinkedList<>();

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order save(Order order) {
        orders.add(order);
        return order;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


}
