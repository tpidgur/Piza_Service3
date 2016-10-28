package ua.rd.pizzaservice.services;


import org.junit.BeforeClass;
import org.junit.Test;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.JpaPizzaRepository;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimplePizzaServiceTest {
    private static SimplePizzaService service;
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);

    @BeforeClass
    public static void createInitialService() {
        JpaPizzaRepository repo = mock(JpaPizzaRepository.class);
        when(repo.find(0L)).thenReturn(new Pizza("Neapolitan Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT));
        when(repo.find(1L)).thenReturn(new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT));
        service = new SimplePizzaService(repo);
    }

    @Test
    public void findTest() {
        assertThat(service.find(0L), is(new Pizza("Neapolitan Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT)));
    }

}
