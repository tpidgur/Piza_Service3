package ua.rd.pizzaservice.services;

import org.springframework.stereotype.Service;
import ua.rd.pizzaservice.domain.Discount;
import ua.rd.pizzaservice.domain.FourPizzaDiscount;
import ua.rd.pizzaservice.domain.PizzaOrder;
import ua.rd.pizzaservice.domain.PizzaCardDiscount;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class SimpleDiscountService implements DiscountService {
    private List<Discount> discounts;

    @PostConstruct
    public void init() {
        if (discounts == null) {
            discounts = Arrays.asList(new FourPizzaDiscount(), new PizzaCardDiscount());
        }
    }


    @Override
    public void placeNewDiscount(Discount discount) {
        discounts.add(discount);
    }

    @BenchMark
    @Override
    public BigDecimal calculateTotalDiscount(PizzaOrder order) {
        return discounts.stream().filter(e -> e.isLiableToDiscount(order)).map(e -> e.calculateDiscount(order))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }
}
