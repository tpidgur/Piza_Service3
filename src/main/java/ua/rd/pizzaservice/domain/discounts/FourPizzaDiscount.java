package ua.rd.pizzaservice.domain.discounts;

import org.springframework.stereotype.Component;
import ua.rd.pizzaservice.domain.Order;

import java.math.BigDecimal;
import java.util.Comparator;


@Component
@org.springframework.core.annotation.Order(value = 1)
public class FourPizzaDiscount implements Discount {

    private static final int PIZZAS_NUMBER_THRESHOLD = 4;
    private static final BigDecimal DISCOUNT_VALUE = new BigDecimal(30).divide(new BigDecimal(100));;


    @Override
    public boolean isApplicableTo(Order order) {
        return order.getPizzas().values().stream().mapToInt(i -> i).sum() > PIZZAS_NUMBER_THRESHOLD;
    }

    @Override
    public BigDecimal calculateDiscount(Order order) {
        return order.getPizzas().keySet().stream()
                .map(i -> i.getPrice().multiply(DISCOUNT_VALUE))
                .max(Comparator.naturalOrder()).orElse(null);
    }

}
