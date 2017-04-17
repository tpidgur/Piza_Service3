package ua.rd.pizzaservice.domain;

import org.junit.Before;
import org.junit.Test;
import ua.rd.pizzaservice.domain.discounts.PizzaCardDiscount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PizzaCardDiscountTest {

    private PizzaCardDiscount discount;
    private Order order;
    private Customer customer;
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);
    public static final BigDecimal CARD_DISCOUNT = new BigDecimal(10).divide(new BigDecimal(100));
    public static final BigDecimal ORDER_DISCOUNT = new BigDecimal(30).divide(new BigDecimal(100));

    @Before
    public void initializeFourPizzaDiscountInstance() {
        discount = new PizzaCardDiscount();
    }

    @Before
    public void initializeOrderInstance() {
        customer = new Customer("Ivan");
        Map<Pizza, Integer> pizzas = new HashMap<>();
        pizzas.put(new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT), 1);
        pizzas.put(new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT), 4);
        order = new Order(pizzas,customer);
//
//        order = new Order(customer, Arrays.asList
//                (new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT),
//                        new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT),
//                        new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETARIAN),
//                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA),
//                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA)));

    }

    @Test
    public void isNotLiableToDiscountTest() {
        boolean hasDiscount = discount.isApplicableTo(order);
        assertThat(hasDiscount, is(false));
    }

    @Test
    public void isLiableToDiscountTest() {
        customer.createNewCardIfNotExist();
        boolean hasDiscount = discount.isApplicableTo(order);
        assertThat(hasDiscount, is(true));
    }

    @Test
    public void makeDiscountTest() {
        customer.createNewCardIfNotExist();
        assertThat(discount.calculateDiscount(order), is(getSmallestDiscount()));
    }

    private BigDecimal getSmallestDiscount() {
        return (getOrderDiscount().compareTo(getPizzaCardDiscount()) == -1) ?
                getOrderDiscount() : getPizzaCardDiscount();

    }

    private BigDecimal getPizzaCardDiscount() {
        return customer.getCard().getBalance().multiply(CARD_DISCOUNT);
    }

    private BigDecimal getOrderDiscount() {
        return order.calculateTotalPrice().multiply(ORDER_DISCOUNT);
    }
}