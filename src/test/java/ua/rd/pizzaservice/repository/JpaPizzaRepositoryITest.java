package ua.rd.pizzaservice.repository;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.Pizza;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class JpaPizzaRepositoryITest extends RepositoryTestConfig {
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);
    public static final Pizza.PizzaType PIZZA_TYPE = Pizza.PizzaType.SEA;
    @Autowired
    private PizzaRepository pizzaRepository;

    @After
    public void intialTune() {
        pizzaRepository.delete();
    }

    @Test
    public void findTest() {
        Pizza expected = initializeOnePizza();
        Pizza actual = pizzaRepository.find(expected.getId());
        assertEquals(actual, expected);
    }


    @Test
    public void findAllByTypeTest() {
        initializeTwoPizzas();
        List<Pizza> expected = Arrays.asList(initializeOnePizza());
        List<Pizza> actual = pizzaRepository.findAllByType(PIZZA_TYPE);
        assertEquals(actual, expected);
    }

    @Test
    public void findAllTest() {
        List<Pizza> expected = initializeTwoPizzas();
        List<Pizza> actual = pizzaRepository.findAll();
        assertEquals(actual, expected);
    }


    @Test
    public void saveTest() {
        Pizza pizza = initializeOnePizza();
        assertNotNull(pizza.getId());
    }

    private Pizza initializeOnePizza() {
        Pizza pizza = new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.SEA);
        return pizzaRepository.save(pizza);
    }

    private List<Pizza> initializeTwoPizzas() {
        Pizza pizza1 = pizzaRepository.save(
                new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT));
        Pizza pizza2 = pizzaRepository.save(
                new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT));
        return Arrays.asList(pizza1, pizza2);
    }


}