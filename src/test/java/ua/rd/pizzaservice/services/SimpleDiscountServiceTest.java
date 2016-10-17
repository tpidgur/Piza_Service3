package ua.rd.pizzaservice.services;

import org.junit.Before;
import org.junit.Test;
import ua.rd.pizzaservice.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleDiscountServiceTest {
    SimpleDiscountService discountService;
    Order order;
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);

    @Before
    public void initializeFourPizzaDiscountInstance() {
        discountService = new SimpleDiscountService();
        discountService.init();
    }

    @Before
    public void initializeOrderInstance() {
        order = new Order(new Customer("Ivan"), asList
                (new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT),
                        new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT),
                        new Pizza("Greek Pizza", PIZZA_PRICE1, Pizza.PizzaType.VEGETERIAN),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA),
                        new Pizza("Sea Pizza", PIZZA_PRICE1, Pizza.PizzaType.SEA)));
        order.getCustomer().createNewCardIfNotExist();
    }

    @Test
    public void calculateTotalDiscount() {
        assertThat(discountService.calculateTotalDiscount(order),
                is(calculateSumOfDiscounts(new FourPizzaDiscount(), new PizzaCardDiscount())));
    }

    private BigDecimal calculateSumOfDiscounts(Discount... discount) {
        return Arrays.stream(discount).map(e -> getTotalDiscount(e)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getTotalDiscount(Discount discount) {
        System.out.println(discount.isLiableToDiscount(order)+""+discount);
        return (discount.isLiableToDiscount(order)) ? discount.calculateDiscount(order) : new BigDecimal(0);
    }


}