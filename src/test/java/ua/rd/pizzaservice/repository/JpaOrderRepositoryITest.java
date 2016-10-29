package ua.rd.pizzaservice.repository;


import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class JpaOrderRepositoryITest extends RepositoryTestConfig {
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JpaCustomerRepository customerRepository;


    @After
    public void intialTune() {
        orderRepository.delete();
        customerRepository.delete();
    }

    @Test
    public void findTest() {
        Order expected = orderRepository.save(initializeOneOrder());
        Order actual = orderRepository.find(expected.getId());
        assertEquals(actual, expected);
    }


    @Test
    public void findAllByCustomerTest() {
        initializeTwoOrdersWithSameCustomer();
        Order order = initializeOneOrder();
        List<Order> actual = orderRepository.findAllByCustomer(order.getCustomer());
        assertEquals(actual, Arrays.asList(order));

    }

    @Test
    public void findAllTest() {
        List<Order> actual = initializeTwoOrdersWithSameCustomer();
        List<Order> expected = orderRepository.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void saveTest() {
        Order order = orderRepository.save(initializeOneOrder());
        assertNotNull(order.getId());
    }

    public Order initializeOneOrder() {
        Map<Pizza, Integer> pizzas = new HashMap<>();
        pizzas.put(new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT), 1);
        pizzas.put(new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT), 4);
        Order order = new Order(pizzas, new Customer("Ivan"));
        return orderRepository.save(order);
//        return orderRepository.save(new Order(new Customer("Ivan"), Arrays.asList
//                (new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT),
//                        new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT),
//                        new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN),
//                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA),
//                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA))));
    }

    public List<Order> initializeTwoOrdersWithSameCustomer() {
        Customer customer = customerRepository.save(new Customer("Iren"));
        Map<Pizza, Integer> pizzas1 = new HashMap<>();
        pizzas1.put(new Pizza("Bavarian", PIZZA_PRICE1, Pizza.PizzaType.MEAT), 1);
        Order order1 = new Order(pizzas1, new Customer("Ivan"));

        Map<Pizza, Integer> pizzas2 = new HashMap<>();
        pizzas2.put(new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT), 1);
        Order order2 = new Order(pizzas2, new Customer("Ivan"));
//        Order order1 = orderRepository.save(new Order(customer, Arrays.asList
//                (new Pizza("Bavarian", PIZZA_PRICE1, Pizza.PizzaType.MEAT))));
//        Order order2 = orderRepository.save(new Order(customer, Arrays.asList
//                (new Pizza("Bavarian", PIZZA_PRICE1, Pizza.PizzaType.MEAT))));
        return Arrays.asList(order1, order2);
    }

}