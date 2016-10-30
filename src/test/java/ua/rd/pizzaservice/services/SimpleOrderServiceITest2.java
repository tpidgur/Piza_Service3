package ua.rd.pizzaservice.services;


import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.pizzaservice.domain.*;
import ua.rd.pizzaservice.repository.CustomerRepository;
import ua.rd.pizzaservice.repository.OrderRepository;
import ua.rd.pizzaservice.repository.PizzaRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/H2WithSpringJPA.xml"})
public class SimpleOrderServiceITest2 {
    public static final long PIZZA_ID1 = 1L;
    public static final long PIZZA_ID2 = 2L;
    public static final long CUSTOMER_ID = 1L;
    @Autowired
    private SimpleOrderService simpleOrderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private DiscountService discountService;

    private static PizzaRepository pizzaRepository;
    private static CustomerRepository customerRepository;

    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);


    @BeforeClass
    public static void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("H2WithSpringJPA.xml");
        pizzaRepository = (PizzaRepository) context.getBean("pizzaRepository");
        pizzaRepository.save(
                new Pizza("Neapolitan Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT));
        pizzaRepository.save(
                new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN));
        pizzaRepository.save(
                new Pizza("New  Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA));

        customerRepository = (CustomerRepository) context.getBean("customerRepository");
        customerRepository.save(new Customer("Ivan"));
    }


    @Test(expected = RuntimeException.class)
    public void placeNewOrderWithExceptionTest() {
        generateNewOrder(15);
    }

    @Test
    public void placeNewOrder() {
        Order actual = simpleOrderService.placeNewOrder(customerRepository.find(CUSTOMER_ID),
                1l, 1l, 2l);
        Order expected = createOneOrder();
        expected.setId(actual.getId());//without
        assertThat(actual, is(expected));
    }

    private Order createOneOrder() {
        Map<Pizza, Integer> pizzas = new HashMap();
        pizzas.put(pizzaRepository.find(PIZZA_ID1), 2);
        pizzas.put(pizzaRepository.find(PIZZA_ID2), 1);
        Customer customer = customerRepository.find(CUSTOMER_ID);
        return new Order(pizzas, customer);
    }


    @Test
    public void changeStatusTest() {
        Order order = generateNewOrder(5);
        simpleOrderService.changeStatus(order.getId(), Order.Status.IN_PROGRESS);
        Order.Status newStatus = order.getStatus();
        assertThat(newStatus, is(Order.Status.IN_PROGRESS));
    }


    private Order generateNewOrder(int pizzasNumber) {
        return simpleOrderService.placeNewOrder(new Customer("Ivan"),
                generatePizzasId(pizzasNumber));
    }

    private Long[] generatePizzasId(int number) {
        Long[] pizzasIDs = new Long[number];
        LongStream.range(0, pizzasIDs.length).forEach(i -> pizzasIDs[(int) i] = PIZZA_ID1);
        return pizzasIDs;
    }


}