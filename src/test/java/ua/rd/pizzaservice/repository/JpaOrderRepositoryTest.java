package ua.rd.pizzaservice.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;

public class JpaOrderRepositoryTest extends RepositoryTestConfig {
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);

    @Autowired
    private OrderRepository orderRepository;


    public Order initializeOrder() {
        return new Order(new Customer("Ivan"), Arrays.asList
                (new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT),
                        new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT),
                        new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA)));
    }

    @Test
    public void saveTest() throws Exception {
        Order order = orderRepository.save(initializeOrder());
        assertNotNull(order.getId());
    }

    @Test
    public void find() throws Exception {

    }

    @Test
    public void findByCustomer() throws Exception {

    }

    @Test
    public void findAll() throws Exception {

    }

}