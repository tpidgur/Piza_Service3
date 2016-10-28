package ua.rd.pizzaservice.services;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.PizzaRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/H2WithSpringJPA.xml"})
public class SimplePizzaServiceITest {
    public static final BigDecimal PIZZA_PRICE1 =BigDecimal.TEN;
    public static final BigDecimal PIZZA_PRICE2 = BigDecimal.ONE;
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private PizzaRepository pizzaRepository;

    @Before
    public void init() {
        initializeTwoPizzas();
    }

    @Test
    public void findTest() {
        Pizza pizza=initializeOnePizza();
        assertThat(pizzaService.find(pizza.getId()).getId(), is(pizza.getId()));
    }

    private Pizza initializeOnePizza() {
        Pizza pizza = new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.SEA);
        return pizzaRepository.save(pizza);
    }

    private List<Pizza> initializeTwoPizzas() {
        Pizza pizza1 = pizzaRepository.save(
                new Pizza("Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT));
        Pizza pizza2 = pizzaRepository.save(
                new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT));
        return Arrays.asList(pizza1, pizza2);
    }
}