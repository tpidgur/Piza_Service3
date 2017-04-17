package ua.rd.pizzaservice.domain;

import org.junit.Before;
import org.junit.Test;
import ua.rd.pizzaservice.domain.discounts.FourPizzaDiscount;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class FourPizzaDiscountTest {

    private static final BigDecimal PRICE1 = new BigDecimal(3);
    private static final BigDecimal PRICE2 = new BigDecimal(1);
    private static final Pizza NEW_YORK = new Pizza("New York Style Pizza", PRICE1, Pizza.PizzaType.MEAT);
    private static final Pizza NEAPOLITAN = new Pizza("Neapolitan Pizza", PRICE2, Pizza.PizzaType.MEAT);

    private static final Map<Pizza, Integer> ENOUGH_PIZZAS_FOR_DISCOUNT = convertToMap(Stream.of(NEAPOLITAN, NEW_YORK, NEW_YORK, NEW_YORK, NEW_YORK));
    private static final Map<Pizza, Integer> NOT_ENOUGH_PIZZAS_FOR_DISCOUNT = convertToMap(Stream.of(NEAPOLITAN, NEW_YORK, NEW_YORK, NEW_YORK));

    private static final Customer CUSTOMER = new Customer("Ivan");
    private static final Order ORDER_LIABLE_TO_DISCOUNT = new Order(ENOUGH_PIZZAS_FOR_DISCOUNT, CUSTOMER);
    private static final Order ORDER_NOT_LIABLE_TO_DISCOUNT = new Order(NOT_ENOUGH_PIZZAS_FOR_DISCOUNT, CUSTOMER);
    private static final BigDecimal DISCOUNT_VALUE = new BigDecimal(30).divide(new BigDecimal(100));

    private FourPizzaDiscount discount;


    @Before
    public void setup() {
        discount = new FourPizzaDiscount();
    }

    private static Map<Pizza, Integer> convertToMap(Stream<Pizza> pizzaStream) {
        return pizzaStream.collect(Collectors.groupingBy(pizza -> pizza, Collectors.reducing(0, pizzaQuantity -> 1, Integer::sum)));
    }

    @Test
    public void shouldHaveDiscount() {
        assertThat(discount.isApplicableTo(ORDER_LIABLE_TO_DISCOUNT), is(true));
    }

    @Test
    public void shouldNotHaveDiscountForLackOfPizzas() {
        assertThat(discount.isApplicableTo(ORDER_NOT_LIABLE_TO_DISCOUNT), is(false));
    }

    @Test
    public void shouldCalculateDiscount() {
        assertThat(discount.calculateDiscount(ORDER_LIABLE_TO_DISCOUNT), is(PRICE1.multiply(DISCOUNT_VALUE)));
    }
}