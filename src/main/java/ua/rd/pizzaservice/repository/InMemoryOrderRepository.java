package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizzaservice.domain.Order;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryOrderRepository implements OrderRepository {
    private static List<Order> orders = new LinkedList<>();


    @Override
    public Order save(Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public Order getOrder(long orderId) {
        Optional<Order> orderOpt = orders.stream().filter(i -> i.getId().equals(orderId)).findFirst();
        if (orderOpt.isPresent()) {
            return orderOpt.get();
        }
        return null;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


}
