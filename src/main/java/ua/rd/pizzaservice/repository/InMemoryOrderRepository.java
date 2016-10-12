package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizzaservice.domain.Order;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryOrderRepository implements OrderRepo {
    private static List<Order> orders = new LinkedList<>();//added static

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order save(Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public Order getOrder(long orderId) {
        Optional<Order> optional = orders.stream().filter(i -> i.getId().equals(orderId)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


}
