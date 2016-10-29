package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.repository.OrderRepository;

public class SimpleOrderService2 implements OrderService {

    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;
    private final DiscountService discountService;

    public SimpleOrderService2(OrderRepository orderRepository,
                               PizzaService pizzaService,
                               DiscountService discountService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
        this.discountService = discountService;
    }

    @Override
    public Order placeNewOrder(Customer customer, Long... pizzasID) {
        return null;
    }
}
