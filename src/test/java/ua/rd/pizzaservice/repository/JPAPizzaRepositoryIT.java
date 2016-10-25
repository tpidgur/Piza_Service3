package ua.rd.pizzaservice.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizzaservice.domain.Pizza;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class JPAPizzaRepositoryIT extends RepositoryTestConfig {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Test
    public void getPizzas() throws Exception {

    }

    @Test
    public void find() throws Exception {
      //  jdbcTemplate.execute("select * from pizza.pizzas");

    }

    //    @Transactional
  //  @Rollback
    @Test
    public void testSavePizza() throws Exception {
        Pizza pizza = new Pizza();
        pizza.setName("Sea");
        pizza.setType(Pizza.PizzaType.SEA);
        pizza.setPrice(new BigDecimal(10));
        pizza = pizzaRepository.save(pizza);
        System.out.println(pizza);
        assertNotNull(pizza);
    }

}