package ua.rd.pizzaservice.repository;


import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.*;

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
    private CustomerRepository customerRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

    @After
    public void intialTune() {

        jdbcTemplate.update("DELETE  FROM  pizzasamount");
        jdbcTemplate.update("DELETE  FROM  orders");
        jdbcTemplate.update("DELETE FROM pizzas");
        jdbcTemplate.update("DELETE FROM customers");
        jdbcTemplate.update("DELETE FROM pizzacards");
        jdbcTemplate.update("DELETE FROM address");
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
        System.out.println(actual);
        List<Order> expected = orderRepository.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void saveTest() {
        Order order = initializeOneOrder();
        System.out.println(order);
        assertNotNull(order.getId());
    }

    public Order initializeOneOrder() {
        Map<Pizza, Integer> pizzas = new HashMap<>();
        Pizza pizza1 = pizzaRepository.save(new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT));
        Pizza pizza2 = pizzaRepository.save(new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT));
        pizzas.put(pizza1, 1);
        pizzas.put(pizza2, 4);
        Customer customer =(new Customer("Ivan"));
        Order order = new Order(pizzas, customer);
        return orderRepository.save(order);
    }


    public List<Order> initializeTwoOrdersWithSameCustomer() {
        Customer customer = customerRepository.save(new Customer("Iren"));
        Map<Pizza, Integer> pizzas1 = new HashMap<>();
        Pizza pizza1 = pizzaRepository.save(new Pizza("Bavarian", PIZZA_PRICE1, Pizza.PizzaType.MEAT));
        pizzas1.put(pizza1, 1);
        Order order1 = orderRepository.save(new Order(pizzas1, customer));

        Map<Pizza, Integer> pizzas2 = new HashMap<>();
        Pizza pizza2 = pizzaRepository.save(new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT));
        pizzas2.put(pizza2, 1);
        Order order2 = orderRepository.save(new Order(pizzas2, customer));
        return Arrays.asList(order1, order2);
    }


}