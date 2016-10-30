package ua.rd.pizzaservice.services;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.InMemoryOrderRepository;
import ua.rd.pizzaservice.repository.InMemoryPizzaRepository;
import ua.rd.pizzaservice.repository.OrderRepository;
import ua.rd.pizzaservice.repository.PizzaRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/H2WithSpringJPA.xml"})
public class SimpleOrderServiceITest {
    @Autowired
    private SimpleOrderService simpleOrderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private PizzaRepository pizzaRepository;

    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    private final long PIZZA_ID = 1;

    @Before
    public void init() {
        Pizza pizza1 = pizzaRepository.save(
                new Pizza("Neapolitan Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT));
        Pizza pizza2 = pizzaRepository.save(
                new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN));
        Pizza pizza3 = pizzaRepository.save(
                new Pizza("New  Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA));
    }


    @Test(expected = RuntimeException.class)
    public void placeNewOrderTest() {
        generateNewOrder(15);
    }


    @Test
    public void changeStatusTest() {
        Order order = generateNewOrder(5);
        simpleOrderService.changeStatus(order.getId(), Order.Status.IN_PROGRESS);
        Order.Status newStatus = order.getStatus();
        assertThat(newStatus, is(Order.Status.IN_PROGRESS));
    }


//    @Test
//    public void addPizzasToExistingOrderTest() {
//        Order order = generateNewOrder(5);
//        System.out.println(order + "!!!!!!!!!!");
//        System.out.println("++++++++++++++" + simpleOrderService);
//        simpleOrderService.addPizzasToExistingOrder(order.getId(), PIZZA_ID1);
//        int pizzasAmount = simpleOrderService.findOrderById(order.getId()).getPizzas().size();
//        assertThat(pizzasAmount, is(6));
//    }




    @Test
    public void placeNewOrder() {
        System.out.println(simpleOrderService.placeNewOrder(new Customer("Ivan"),
                1l, 2l));

    }

    private Order generateNewOrder(int pizzasNumber) {
        System.out.println("SimpleOrderservice created " + simpleOrderService);
        return simpleOrderService.placeNewOrder(new Customer("Ivan"),
                generatePizzasId(pizzasNumber));
    }

    private long[] generatePizzasId(int number) {
       long[] pizzasIDs = new long[number];
        LongStream.range(0, pizzasIDs.length).forEach(i -> pizzasIDs[(int) i] = PIZZA_ID);
        return pizzasIDs;
    }


}