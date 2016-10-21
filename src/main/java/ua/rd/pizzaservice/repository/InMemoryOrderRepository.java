package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizzaservice.domain.PizzaOrder;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryOrderRepository implements OrderRepository {
    private static List<PizzaOrder> orders = new LinkedList<>();

    @BenchMark
    @Override
    public PizzaOrder save(PizzaOrder order) {
        orders.add(order);
        return order;
    }

    @Override
    public PizzaOrder getOrder(long orderId) {
        Optional<PizzaOrder> orderOpt = orders.stream().filter(i -> i.getId().equals(orderId)).findFirst();
        if (orderOpt.isPresent()) {
            return orderOpt.get();
        }
        return null;
    }

    public List<PizzaOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<PizzaOrder> orders) {
        this.orders = orders;
    }


}
