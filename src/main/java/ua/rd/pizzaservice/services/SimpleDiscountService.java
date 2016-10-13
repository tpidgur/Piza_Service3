package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Discount;
import ua.rd.pizzaservice.domain.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SimpleDiscountService implements DiscountService {
    List<Discount> discounts=new ArrayList<>();

    @Override
    public void placeNewDiscount(Discount discount) {
        discounts.add(discount);
    }

    @Override
    public BigDecimal calculateTotalDiscount(Order order) {
        return null;
    }
}
