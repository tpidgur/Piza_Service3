package ua.rd.pizzaservice.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;
@Component
public class FourPizzaDiscount extends Discount {
    public static final BigDecimal DISCOUNT_MULTIPLICAND = new BigDecimal(30);
    public static final int MIN_PIZZAS_NUMBER = 4;


    @Override
    public boolean isLiableToDiscount(PizzaOrder order) {
        return order.getPizzas().size() > MIN_PIZZAS_NUMBER;
    }

    @Override
    public BigDecimal calculateDiscount(PizzaOrder order) {
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
