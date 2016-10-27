package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryOrderRepository implements OrderRepository {
    private static List<Order> orders = new LinkedList<>();


    @Override
    public Order find(long orderId) {
        Optional<Order> orderOpt = orders.stream().filter(i -> i.getId().equals(orderId)).findFirst();
        if (orderOpt.isPresent()) {
            return orderOpt.get();
        }
        return null;
    }

    @Override
    public List<Order> findAllByCustomer(Customer customer) {
        return null;
    }

    public List<Order> findAll() {
        return orders;
    }


    @BenchMark
    @Override
    public Order save(Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public void delete() {

    }
}
