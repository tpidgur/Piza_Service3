package ua.rd.pizzaservice.domain;

import java.math.BigDecimal;

public class PizzaCardDiscount extends Discount {
    public static final BigDecimal CARD_DISCOUNT = new BigDecimal(10).divide(new BigDecimal(100));
    public static final BigDecimal ORDER_DISCOUNT = new BigDecimal(30).divide(new BigDecimal(100));

    @Override
    public boolean isLiableToDiscount(Order order) {
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
