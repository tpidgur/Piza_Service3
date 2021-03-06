package ua.rd.pizzaservice.domain.discounts;

import org.springframework.stereotype.Component;
import ua.rd.pizzaservice.domain.Order;

import java.math.BigDecimal;
@Component
@org.springframework.core.annotation.Order(value=1)
public class PizzaCardDiscount implements Discount {
    public static final BigDecimal CARD_DISCOUNT = new BigDecimal(10).divide(new BigDecimal(100));
    public static final BigDecimal ORDER_DISCOUNT = new BigDecimal(30).divide(new BigDecimal(100));

    @Override
    public boolean isApplicableTo(Order order) {
        return order.getCustomer().getCard() != null;
    }

    @Override
    public BigDecimal calculateDiscount(Order order) {
        return (getOrderDiscount(order).compareTo(getPizzaCardDiscount(order)) == -1) ?
                getOrderDiscount(order) : getPizzaCardDiscount(order);
    }


    private BigDecimal getPizzaCardDiscount(Order order) {
        return order.getCustomer().getCard().getBalance().multiply(CARD_DISCOUNT);
    }


    private BigDecimal getOrderDiscount(Order order) {
        return order.calculateTotalPrice().multiply(ORDER_DISCOUNT);
    }
}
