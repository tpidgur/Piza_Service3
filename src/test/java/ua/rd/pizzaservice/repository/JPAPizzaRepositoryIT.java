package ua.rd.pizzaservice.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.Pizza;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class JPAPizzaRepositoryIT extends RepositoryTestConfig {
    public static final Long PIZZA_ID = new Long(1);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);
    @Autowired
    private PizzaRepository pizzaRepository;


    @Test
    public void getPizzas() throws Exception {

    }

    @Test
    public void findTest() throws Exception {
        Pizza expected = new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT);
        expected = pizzaRepository.save(expected);
        Pizza actual = pizzaRepository.find(expected.getId());
        assertEquals(expected,actual);
    }

    //  @Transactional
    //  @Rollback
    @Test
    public void saveTest() throws Exception {
        Pizza pizza = new Pizza();
        pizza.setName("Sea");
        pizza.setType(Pizza.PizzaType.SEA);
        pizza.setPrice(new BigDecimal(10));
        pizza = pizzaRepository.save(pizza);
        System.out.println(pizza);
        assertNotNull(pizza);
    }

}