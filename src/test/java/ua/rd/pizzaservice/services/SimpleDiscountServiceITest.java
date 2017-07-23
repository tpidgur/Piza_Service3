package ua.rd.pizzaservice.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.pizzaservice.domain.*;
import ua.rd.pizzaservice.domain.discounts.Discount;
import ua.rd.pizzaservice.domain.discounts.FourPizzaDiscount;
import ua.rd.pizzaservice.domain.discounts.PizzaCardDiscount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/H2WithSpringJPA.xml"})
public class SimpleDiscountServiceITest {
  @Autowired
    SimpleDiscountService discountService;
    Order order;
    public static final BigDecimal PIZZA_PRICE1 = new BigDecimal(3);
    public static final BigDecimal PIZZA_PRICE2 = new BigDecimal(1);


    @Before
    public void initializeOrderInstance() {
        Map<Pizza, Integer> pizzas = new HashMap<>();
        pizzas.put(new Pizza("Neapolitan Pizza", PIZZA_PRICE2, Pizza.PizzaType.MEAT), 1);
        pizzas.put(new Pizza("New York Style Pizza", PIZZA_PRICE1, Pizza.PizzaType.MEAT), 4);
        order = new Order(pizzas,new Customer("Ivan"));
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
        return (discount.isApplicableTo(order)) ? discount.calculateDiscount(order) : new BigDecimal(0);
    }


}