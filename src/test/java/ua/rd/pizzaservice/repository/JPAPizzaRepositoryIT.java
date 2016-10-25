package ua.rd.pizzaservice.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.Pizza;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class JPAPizzaRepositoryIT extends RepositoryTestConfig {
    public static final Long PIZZA_ID = new Long(1);
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);
    public static final Pizza.PizzaType PIZZA_TYPE = Pizza.PizzaType.SEA;
    @Autowired
    private PizzaRepository pizzaRepository;


    @Test
    public void findTest() {
        Pizza actual = initializeOnePizza();
        Pizza expected = pizzaRepository.find(actual.getId());
        assertEquals(expected, actual);
    }

    private Pizza initializeOnePizza() {
        Pizza pizza = new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.SEA);
        return pizzaRepository.save(pizza);
    }

    @Test
    public void findAllByTypeTest() {
        initializeTwoPizzas();
        List<Pizza> actual = new ArrayList<>(Arrays.asList(initializeOnePizza()));
        List<Pizza> expected=pizzaRepository.findAllByType(PIZZA_TYPE);
        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() {
        List<Pizza> actual = initializeTwoPizzas();
        List<Pizza> expected = pizzaRepository.findAll();
        assertEquals(expected, actual);
    }

    private List<Pizza> initializeTwoPizzas() {
        Pizza pizza1 = new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT);
        Pizza pizza2 = new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT);
        pizza1 = pizzaRepository.save(pizza1);
        pizza2 = pizzaRepository.save(pizza2);
        return new ArrayList<>(Arrays.asList(pizza1,pizza2));
    }

    //  @Transactional
    //  @Rollback
    @Test
    public void saveTest() {
        Pizza pizza = new Pizza();
        pizza.setName("Sea");
        pizza.setType(Pizza.PizzaType.SEA);
        pizza.setPrice(new BigDecimal(10));
        pizza = pizzaRepository.save(pizza);
        System.out.println(pizza);
        assertNotNull(pizza);
    }

}