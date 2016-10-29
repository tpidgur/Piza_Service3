package ua.rd.pizzaservice.domain;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;

@Component
@org.springframework.core.annotation.Order(value=1)
public class FourPizzaDiscount extends Discount {
    public static final BigDecimal DISCOUNT_MULTIPLICAND = new BigDecimal(30);
    public static final int MIN_PIZZAS_NUMBER = 4;


    @Override
    public boolean isLiableToDiscount(Order order) {
        return order.getPizzas().values().stream().mapToInt(i->i).sum() > MIN_PIZZAS_NUMBER;
    }

    @Override
    public BigDecimal calculateDiscount(Order order) {
        Optional<BigDecimal> maxPriceOpt = order.getPizzas().keySet().stream()
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
