package ua.rd.pizzaservice.domain.discounts;

import ua.rd.pizzaservice.domain.Order;

import java.math.BigDecimal;


public interface Discount {

    boolean isApplicableTo(Order order);

    BigDecimal calculateDiscount(Order order);
}
