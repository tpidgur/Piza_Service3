package ua.rd.pizzaservice.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class OrderTest {
    public static final BigDecimal DISCOUNT_MULTIPLICAND = new BigDecimal(0.3);
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);
    public static final BigDecimal INITIAL_BALANCE = new BigDecimal(100);
    Order order;

    @Before
    public void initializeOrder() {
        order = new Order(new Customer("Ivan"), Arrays.asList
                (new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT),
                        new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT),
                        new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA)));
    }

    @Test
    public void provideDiscountTest() {
        assertThat(order.calculateDiscountForFourPizzas(), is(PIZZA_PRICE1.multiply(DISCOUNT_MULTIPLICAND)));
    }



    private BigDecimal getTotalOrderPriceWithDiscount() {
        BigDecimal onePizzaPriceWithDiscount = PIZZA_PRICE1.subtract(PIZZA_PRICE1.multiply(DISCOUNT_MULTIPLICAND));
        return onePizzaPriceWithDiscount.add(PIZZA_PRICE1.multiply(new BigDecimal(3))).
                add(PIZZA_PRICE2);
    }

    @Test
    public void getCumulativeCardDiscountTest() {
        Customer customer = order.getCustomer();
        customer.createNewCard();
        PizzaCard card = order.getCustomer().getCard();
        card.setBalance(INITIAL_BALANCE);
        BigDecimal discount = order.calculateDiscountFromCummulativeCard();
        assertThat(discount,is(PIZZA_PRICE1.multiply(new BigDecimal(4)).add(PIZZA_PRICE2).multiply(new BigDecimal(0.3))));


    }

}