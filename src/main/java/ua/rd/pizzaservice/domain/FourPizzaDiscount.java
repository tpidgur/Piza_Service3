package ua.rd.pizzaservice.domain;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;

public class FourPizzaDiscount extends Discount {

    public static final int MIN_PIZZAS_NUMBER = 4;
    public static final BigDecimal DISCOUNT_MULTIPLICAND = new BigDecimal(30);
    public static final BigDecimal ONE_HUNDRED_PERCENTS = new BigDecimal(100);

    @Override
    public boolean isLiableToDiscount(Order order) {
        return order.getPizzas().size() > MIN_PIZZAS_NUMBER;
    }

    @Override
    public BigDecimal calculateDiscount(Order order) {
        Optional<BigDecimal> maxPriceOpt = order.getPizzas().stream()
                .map(i -> i.getPrice()).max(Comparator.naturalOrder());
        if (maxPriceOpt.isPresent()) {
            BigDecimal discountAmount = maxPriceOpt.get().multiply(calculatePercentageRatio());
            return discountAmount;
        }
        return null;
    }

    private BigDecimal calculatePercentageRatio() {
        return DISCOUNT_MULTIPLICAND.divide(ONE_HUNDRED_PERCENTS);
    }
}
