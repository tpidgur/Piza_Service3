package ua.rd.pizzaservice.domain;

import java.math.BigDecimal;

public class FourPizzaDiscount extends Discount {
    @Override
    public boolean isLiableToDiscount(Order order) {
        return order.getPizzas().size() > 4;
    }

    @Override
    public BigDecimal calculateDiscount(Order order) {
return null;
    }
}
