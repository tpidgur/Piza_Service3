package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.discounts.Discount;
import ua.rd.pizzaservice.domain.Order;

import java.math.BigDecimal;

public interface DiscountService {
    void placeNewDiscount(Discount discount);
    BigDecimal calculateTotalDiscount(Order order);
}
