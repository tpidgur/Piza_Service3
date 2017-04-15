package ua.rd.pizzaservice.services;


import org.hibernate.Session;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizzaservice.domain.*;
import ua.rd.pizzaservice.repository.CustomerRepository;
import ua.rd.pizzaservice.repository.OrderRepository;
import ua.rd.pizzaservice.repository.PizzaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/H2WithSpringJPA.xml"})
public class SimpleOrderServiceITest {
    @PersistenceContext
    protected EntityManager em;

    public static final Order.Status CANCELLED = Order.Status.CANCELLED;
    public static final Order.Status DONE = Order.Status.DONE;
    @Autowired
    private SimpleOrderService simpleOrderService;
    @Autowired
    private OrderRepository orderRepository;


    private static PizzaRepository pizzaRepository;
    private static CustomerRepository customerRepository;

    public static long PIZZA_ID1;
    public static long PIZZA_ID2;
    public static long PIZZA_ID3;



    public static final int AMOUNT1 = 1;
    public static final int AMOUNT2 = 2;
    public static final int AMOUNT3 = 3;

    public static long CUSTOMER_ID;
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);

    public static final Order.Status IN_PROGRESS = Order.Status.IN_PROGRESS;


    @BeforeClass
    public static void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("H2WithSpringJPA.xml");
        pizzaRepository = (PizzaRepository) context.getBean("pizzaRepository");
        Pizza pizza1 = pizzaRepository.save(
                new Pizza("Neapolitan Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT));
        Pizza pizza2 = pizzaRepository.save(
                new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETARIAN));
        Pizza pizza3 = pizzaRepository.save(
                new Pizza("New  Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA));
        PIZZA_ID1 = pizza1.getPizzaId();
        System.out.println(PIZZA_ID1);
        PIZZA_ID2 = pizza2.getPizzaId();
        PIZZA_ID3 = pizza3.getPizzaId();
        customerRepository = (CustomerRepository) context.getBean("customerRepository");
        Customer customer = customerRepository.save(new Customer("Ivan"));
        CUSTOMER_ID = customer.getCustomerId();
    }


    @Test(expected = RuntimeException.class)
    public void placeNewOrderWithExceptionTest() {
        createManyPizzaOrder(15);
    }

    private Order createManyPizzaOrder(int pizzaNumber) {
        Customer customer = customerRepository.find(CUSTOMER_ID);
        Long[] pizzasIds = new Long[pizzaNumber];
        for (int i = 0; i < pizzaNumber; i++) {
            pizzasIds[i] = PIZZA_ID1;
        }
        return simpleOrderService.placeNewOrder(customer, pizzasIds);
    }


    @Test
    public void placeNewOrder() {
        Order actual = simpleOrderService.placeNewOrder(customerRepository.find(CUSTOMER_ID),
                PIZZA_ID1, PIZZA_ID1, PIZZA_ID2);
        Order expected = createOrder();
        expected.setOrderId(actual.getOrderId());
        assertThat(actual, is(expected));
    }

    private Order createOrder() {
        Map<Pizza, Integer> pizzas = new HashMap();
        pizzas.put(pizzaRepository.find(PIZZA_ID1), AMOUNT2);
        pizzas.put(pizzaRepository.find(PIZZA_ID2), AMOUNT1);
        Customer customer = customerRepository.find(CUSTOMER_ID);
        return new Order(pizzas, customer);
    }

    @Test
    public void convertIdListInIdMapTest() {
        Map<Long, Integer> actual = simpleOrderService.convertIdListInIdMap(
                Arrays.asList(PIZZA_ID1, PIZZA_ID1, PIZZA_ID1, PIZZA_ID2, PIZZA_ID2, PIZZA_ID3));
        Map<Long, Integer> expected = getMapOfIdsWithTheirQuantites();
        assertThat(actual, is(expected));
    }

    private Map<Long, Integer> getMapOfIdsWithTheirQuantites() {
        Map<Long, Integer> expected = new HashMap<>();
        expected.put(PIZZA_ID1, AMOUNT3);
        expected.put(PIZZA_ID2, AMOUNT2);
        expected.put(PIZZA_ID3, AMOUNT1);
        return expected;
    }

    @Test
    public void convertIdMapInPizzaMapTest() {
        Map<Pizza, Integer> actual = simpleOrderService
                .convertIdMapInPizzaMap(getMapOfIdsWithTheirQuantites());
        Map<Pizza, Integer> expected = getMapOfPizzasWithTheirQuantities();
        assertThat(actual, is(expected));
    }

    private Map<Pizza, Integer> getMapOfPizzasWithTheirQuantities() {
        Map<Pizza, Integer> expected = new HashMap<>();
        expected.put(pizzaRepository.find(PIZZA_ID1), AMOUNT3);
        expected.put(pizzaRepository.find(PIZZA_ID2), AMOUNT2);
        expected.put(pizzaRepository.find(PIZZA_ID3), AMOUNT1);
        return expected;
    }

    @Transactional
    @Test
    public void addPizzasToExistingOrderTest() {
        Order order = placeNewSingleOrder();
        getSession().get(Order.class, order.getOrderId()).getPizzas().size();
        simpleOrderService.addPizzasToExistingOrder(order.getOrderId(), PIZZA_ID1, PIZZA_ID2);
        int pizzasAmount = simpleOrderService.find(order.getOrderId()).getAmountOfPizzas();
        assertThat(pizzasAmount, is(5));
    }

    private Session getSession() {
        return em.unwrap(Session.class);
    }

    @Test(expected = RuntimeException.class)
    public void addPizzasToExistingOrderTest2() {
        Order order = createManyPizzaOrder(10);
        simpleOrderService.addPizzasToExistingOrder(order.getOrderId(), PIZZA_ID1);
    }


    @Test
    public void changeStatusTest() {
        Order order = placeNewSingleOrder();
        simpleOrderService.changeStatus(order.getOrderId(), IN_PROGRESS);
        Order.Status actual = simpleOrderService.find(order.getOrderId()).getStatus();
        assertThat(actual, is(IN_PROGRESS));
    }

    @Test
    public void findOrderByIdTest() {
        Order order = placeNewSingleOrder();
        Order actual = simpleOrderService.find(order.getOrderId());
        Order expected = orderRepository.find(order.getOrderId());
        assertThat(actual, is(expected));
    }

    @Transactional
    @Test
    public void removePizzaFromExistingOrderTest() {
        Order order = placeNewSingleOrder();
        getSession().get(Order.class, order.getOrderId()).getPizzas().size();
        simpleOrderService.removePizzaFromExistingOrder(order.getOrderId(), PIZZA_ID1);
        Order actual = simpleOrderService.find(order.getOrderId());
        assertThat(actual.getPizzas(), is(getMapOfPizzasWithTheirQuantities2()));
    }

    private Map<Pizza, Integer> getMapOfPizzasWithTheirQuantities2() {
        Map<Pizza, Integer> expected = new HashMap<>();
        expected.put(pizzaRepository.find(PIZZA_ID1), AMOUNT1);
        expected.put(pizzaRepository.find(PIZZA_ID2), AMOUNT1);
        return expected;
    }

    @Test(expected = RuntimeException.class)
    public void removePizzaFromExistingOrderTestFails() {
        Order order = placeNewSingleOrder();
        simpleOrderService.removePizzaFromExistingOrder(order.getOrderId(), PIZZA_ID2);
        simpleOrderService.removePizzaFromExistingOrder(order.getOrderId(), PIZZA_ID2);
        System.out.println(simpleOrderService.find(order.getOrderId()));

    }

    @Test
    public void setCancelStatusTest() {
        Order order = placeNewSingleOrder();
        simpleOrderService.setCancelStatus(order.getOrderId());
        Order.Status actual = simpleOrderService.find(order.getOrderId()).getStatus();
        assertThat(actual, is(CANCELLED));
    }

    @Test
    public void setInProgressStatusTest() {
        Order order = placeNewSingleOrder();
        simpleOrderService.setInProgressStatus(order.getOrderId());
        Order.Status actual = simpleOrderService.find(order.getOrderId()).getStatus();
        assertThat(actual, is(IN_PROGRESS));
    }

    @Transactional
    @Test
    public void setDoneStatusTest() {
        Order order = placeNewSingleOrder();
        getSession().get(Order.class, order.getOrderId()).getPizzas().size();//eager loading of the pizzas Map
        simpleOrderService.setInProgressStatus(order.getOrderId());
        simpleOrderService.setDoneStatus(order.getOrderId());
        Order.Status actual = simpleOrderService.find(order.getOrderId()).getStatus();
        assertThat(actual, is(DONE));
        assertThat(getCurrentBalance(order).compareTo(BigDecimal.ZERO), is(1));
    }

    private BigDecimal getCurrentBalance(Order order) {
        return simpleOrderService.find(order.getOrderId())
                .getCustomer().getCard().getBalance();
    }


    private Order placeNewSingleOrder() {
        return simpleOrderService.placeNewOrder(customerRepository.find(CUSTOMER_ID),
                PIZZA_ID1, PIZZA_ID1, PIZZA_ID2);
    }
}