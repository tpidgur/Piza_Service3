package ua.rd.pizzaservice.services;

import org.junit.Before;
import org.junit.Test;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.PizzaOrder;
import ua.rd.pizzaservice.repository.InMemoryOrderRepository;
import ua.rd.pizzaservice.repository.InMemoryPizzaRepository;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class SimpleOrderServiceTest {
    private SimpleOrderService simpleOrderService;
    private final long PIZZA_ID = 1;

    @Before
    public void initializeOrder() {
        InMemoryPizzaRepository inMemoryPizzaRepository = new InMemoryPizzaRepository();
        inMemoryPizzaRepository.init();
        SimpleDiscountService discountService=new SimpleDiscountService();
        simpleOrderService = new SimpleOrderService(new InMemoryOrderRepository(),
                new SimplePizzaService(inMemoryPizzaRepository), discountService);
    }

    @Test(expected = RuntimeException.class)
    public void placeNewOrderTest() {
        generateNewOrder(15);
    }

    private PizzaOrder generateNewOrder(int pizzasNumber) {
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
        PizzaOrder order = generateNewOrder(5);
        simpleOrderService.changeStatus(order.getId(), PizzaOrder.Status.IN_PROGRESS);
        PizzaOrder.Status newStatus = order.getStatus();
        assertThat(newStatus, is(PizzaOrder.Status.IN_PROGRESS));
    }



    @Test
    public void addPizzasToExistingOrderTest() {
        PizzaOrder order = generateNewOrder(5);
        simpleOrderService.addPizzasToExistingOrder(order.getId(), PIZZA_ID);
        int pizzasAmount = simpleOrderService.findOrderById(order.getId()).getPizzas().size();
        assertThat(pizzasAmount, is(6));
    }

}