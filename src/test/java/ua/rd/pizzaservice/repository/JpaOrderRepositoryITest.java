package ua.rd.pizzaservice.repository;


import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Rollback
public class JpaOrderRepositoryITest extends RepositoryTestConfig {
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JpaCustomerRepository customerRepository;


    public Order initializeOrder() {
        return new Order(new Customer("Ivan"), Arrays.asList
                (new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT),
                        new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT),
                        new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA)));
    }

    public List<Order> initializeTwoOrders() {
        Customer customer = customerRepository.save(new Customer("Iren"));
        Order order1 = orderRepository.save(new Order(customer, Arrays.asList
                (new Pizza("Bavarian", PIZZA_PRICE1, Pizza.PizzaType.MEAT))));
        Order order2 = orderRepository.save(new Order(customer, Arrays.asList
                (new Pizza("Bavarian", PIZZA_PRICE1, Pizza.PizzaType.MEAT))));
        return Arrays.asList(order1, order2);
    }


    @Test
    public void saveTest() {
        Order order = orderRepository.save(initializeOrder());
        assertNotNull(order.getId());
    }

    @Test
    public void findTest() {
        Order expected = orderRepository.save(initializeOrder());
        Order actual = orderRepository.find(expected.getId());
        assertEquals(actual, expected);
    }

    @Test
    public void findByCustomerTest() {
        Customer customer = customerRepository.save(new Customer("Iren"));
        Order order1 = orderRepository.save(new Order(customer, Arrays.asList
                (new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT),
                        new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN))));

        Order order2 = orderRepository.save(new Order(customer, Arrays.asList(
                new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN))));
        Order order3 = orderRepository.save(initializeOrder());
        List<Order> actual = orderRepository.findByCustomer(customer);
        assertEquals(actual, Arrays.asList(order1, order2));
    }


    @Test
    @Ignore
    public void findByCustomerTest1() {
        Customer customer = new Customer("Orest");
        customer.setId(new Long(10));
        JpaCustomerRepository repository = mock(JpaCustomerRepository.class);
        when(repository.find(new Long(10))).thenReturn(customer);
        Order order1 = orderRepository.save(new Order(repository.find(new Long(10)), Arrays.asList
                (new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT),
                        new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN))));

        Order order2 = orderRepository.save(new Order(repository.find(new Long(10)), Arrays.asList(
                new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN))));
        Order order3 = orderRepository.save(initializeOrder());
        System.out.println(order1);
        System.out.println(order2);
        System.out.println(order3);
        List<Order> actual = orderRepository.findByCustomer(customer);
        System.out.println(actual + "actual");
        System.out.println(customer + "customer");
        assertEquals(actual, Arrays.asList(order1));
    }


    @Test
    public void findAllTest() {
        List<Order> actual = initializeTwoOrders();
        List<Order> expected = orderRepository.findAll();
        assertEquals(expected, actual);
    }

}