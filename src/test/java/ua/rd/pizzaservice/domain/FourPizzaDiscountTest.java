package ua.rd.pizzaservice.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class FourPizzaDiscountTest {
    private FourPizzaDiscount fourPizzaDiscount;
    private Order order;
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);
    public static final BigDecimal DISCOUNT_MULTIPLICAND = new BigDecimal(0.3);
    @Before
    public void initializeFourPizzaDiscountInstance() {
        fourPizzaDiscount = new FourPizzaDiscount();
    }

    @Before
    public void initializeOrderInstance() {
        order = new Order(new Customer("Ivan"), Arrays.asList
                (new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT),
                        new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT),
                        new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA)));
    }

    @Test
    public void isLiableToDiscountTest() {
        boolean hasDiscount = fourPizzaDiscount.isLiableToDiscount(order);
        assertThat(hasDiscount, is(true));
    }

    @Test
    public void makeDiscountTest() {
        BigDecimal discount = fourPizzaDiscount.calculateDiscount(order);
        assertThat(discount, is(PIZZA_PRICE1.multiply(DISCOUNT_MULTIPLICAND)));
    }
}