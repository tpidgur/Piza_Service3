package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Discount;
import ua.rd.pizzaservice.domain.FourPizzaDiscount;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.PizzaCardDiscount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleDiscountService implements DiscountService {
    private List<Discount> discounts;

    public void init() {
        discounts=   Arrays.asList(new FourPizzaDiscount(), new PizzaCardDiscount());

    }

    @Override
    public void placeNewDiscount(Discount discount) {
        discounts.add(discount);
    }

    @Override
    public BigDecimal calculateTotalDiscount(Order order) {
        return discounts.stream().filter(e -> e.isLiableToDiscount(order)).map(e -> e.calculateDiscount(order))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }
}
