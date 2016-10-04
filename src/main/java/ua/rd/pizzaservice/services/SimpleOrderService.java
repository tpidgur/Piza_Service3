package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.InMemoryOrderRepository;
import ua.rd.pizzaservice.repository.OrderRepo;
import ua.rd.pizzaservice.repository.PizzaRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SimpleOrderService implements OrderService {

    private final OrderRepo orderRepo ;
            //new InMemoryOrderRepository();
    private final PizzaService pizzaService ;
                    //new SimplePizzaService();


    public SimpleOrderService(OrderRepo orderRepo, PizzaService pizzaService) {
        this.orderRepo = orderRepo;
        this.pizzaService = pizzaService;
    }

    public Order placeNewOrder(Customer customer, Long... pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();
        for (Long id : pizzasID) {
            pizzas.add(findPizaById(id));  // get Pizza from predifined in-memory list
        }
        Order newOrder = new Order(customer, pizzas);
      orderRepo.save(newOrder);

        return newOrder;
    }


    Pizza findPizaById(Long id) {
        return pizzaService.find(id);
    }



}
