package ua.rd.pizzaservice.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.repository.InMemoryOrderRepository;
import ua.rd.pizzaservice.repository.InMemoryPizzaRepository;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/repoContext.xml","/appContext.xml"})
public class SimpleOrderServiceTest {
    @Autowired
    private SimpleOrderService simpleOrderService;
    private final long PIZZA_ID = 1;

    @Before
    public void initializeOrder() {
        InMemoryPizzaRepository inMemoryPizzaRepository = new InMemoryPizzaRepository();
        inMemoryPizzaRepository.init();
       // SimpleDiscountService discountService=new SimpleDiscountService();
//        simpleOrderService = new SimpleOrderService(new InMemoryOrderRepository(),
//                new SimplePizzaService(inMemoryPizzaRepository), discountService);
    }


    @Test(expected = RuntimeException.class)
    public void placeNewOrderTest() {
        generateNewOrder(15);
    }

    private Order generateNewOrder(int pizzasNumber) {
        System.out.println("SimpleOrderservice created "+simpleOrderService);
        return simpleOrderService.placeNewOrder(new Customer("Ivan"),
                generatePizzasId(pizzasNumber));
    }

    private Long[] generatePizzasId(int number) {
        Long[] pizzasIDs = new Long[number];
        IntStream.range(0, pizzasIDs.length).forEach(i -> pizzasIDs[i] = PIZZA_ID);
        return pizzasIDs;
    }

    @Test
    public void changeStatusTest() {
        Order order = generateNewOrder(5);
        simpleOrderService.changeStatus(order.getId(), Order.Status.IN_PROGRESS);
        Order.Status newStatus = order.getStatus();
        assertThat(newStatus, is(Order.Status.IN_PROGRESS));
    }



    @Test
    public void addPizzasToExistingOrderTest() {
        Order order = generateNewOrder(5);
        simpleOrderService.addPizzasToExistingOrder(order.getId(), PIZZA_ID);
        int pizzasAmount = simpleOrderService.findOrderById(order.getId()).getPizzas().size();
        assertThat(pizzasAmount, is(6));
    }

}