package ua.rd.pizzaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.pizzaservice.domain.discounts.Discount;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SimpleDiscountService implements DiscountService {
    private List<Discount> discounts;

//    @PostConstruct
//    public void init() {
//        if (discounts == null) {
//            discounts = Arrays.asList(new FourPizzaDiscount(), new PizzaCardDiscount());
//        }
//    }

    @Autowired
    public SimpleDiscountService(List<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public void placeNewDiscount(Discount discount) {
        discounts.add(discount);
    }

    @BenchMark
    @Override
    public BigDecimal calculateTotalDiscount(Order order) {
        return discounts.stream().filter(e -> e.isApplicableTo(order)).map(e -> e.calculateDiscount(order))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }
}
