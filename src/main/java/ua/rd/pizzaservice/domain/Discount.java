package ua.rd.pizzaservice.domain;

import java.math.BigDecimal;

public abstract class Discount {
    public abstract boolean isLiableToDiscount(Order order);

    public abstract BigDecimal calculateDiscount(Order order);
}
