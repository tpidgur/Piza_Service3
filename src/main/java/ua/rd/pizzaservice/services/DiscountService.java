package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Discount;
import ua.rd.pizzaservice.domain.PizzaOrder;

import java.math.BigDecimal;

public interface DiscountService {
    void placeNewDiscount(Discount discount);
    BigDecimal calculateTotalDiscount(PizzaOrder order);
}
