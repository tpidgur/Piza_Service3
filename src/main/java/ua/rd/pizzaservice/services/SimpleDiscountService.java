package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Discount;

import java.util.ArrayList;
import java.util.List;

public class SimpleDiscountService implements DiscountService {
    List<Discount> discounts=new ArrayList<>();

    @Override
    public void placeNewDiscount(Discount discount) {
        discounts.add(discount);
    }
}
