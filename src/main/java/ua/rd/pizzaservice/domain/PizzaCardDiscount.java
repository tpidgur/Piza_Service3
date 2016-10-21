package ua.rd.pizzaservice.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class PizzaCardDiscount extends Discount {
    public static final BigDecimal CARD_DISCOUNT = new BigDecimal(10).divide(new BigDecimal(100));
    public static final BigDecimal ORDER_DISCOUNT = new BigDecimal(30).divide(new BigDecimal(100));

    @Override
    public boolean isLiableToDiscount(PizzaOrder order) {
        return order.getCustomer().getCard() != null;
    }

    @Override
    public BigDecimal calculateDiscount(PizzaOrder order) {
        return (getOrderDiscount(order).compareTo(getPizzaCardDiscount(order)) == -1) ?
                getOrderDiscount(order) : getPizzaCardDiscount(order);
    }


    private BigDecimal getPizzaCardDiscount(PizzaOrder order) {
        return order.getCustomer().getCard().getBalance().multiply(CARD_DISCOUNT);
    }


    private BigDecimal getOrderDiscount(PizzaOrder order) {
        return order.calculateTotalPrice().multiply(ORDER_DISCOUNT);
    }
}
