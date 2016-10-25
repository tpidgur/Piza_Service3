package ua.rd.pizzaservice.domain;

import java.math.BigDecimal;

public abstract class Discount {

    public static final BigDecimal ONE_HUNDRED_PERCENTS = new BigDecimal(100);

    public abstract boolean isLiableToDiscount(Order order);

    public abstract BigDecimal calculateDiscount(Order order);
}
