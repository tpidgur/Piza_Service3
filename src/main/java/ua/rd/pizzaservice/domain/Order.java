package ua.rd.pizzaservice.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@Scope("prototype")
public class Order {
    private Long id;
    private List<Pizza> pizzas;
    private Customer customer;
    private static long counter;
    private Status status;
    private final int amountOfPizzasForDicount = 4;
    private BigDecimal discountMultiplier = new BigDecimal(0.3);
    public static final BigDecimal CUMMULATIVE_CARD_DISCOUNT_COEF = new BigDecimal(0.1);
    public static final BigDecimal TOTAL_ORDER_DISCOUNT = new BigDecimal(0.3);


    public enum Status {
        NEW, IN_PROGRESS, CANCELLED, DONE
    }

    public Order(Customer customer, List<Pizza> pizzas) {
        this.pizzas = pizzas;
        this.customer = customer;
        this.status = Status.NEW;
        id = counter++;
    }


    public BigDecimal calculateDiscountForFourPizzas() {
        if (pizzas.size() > amountOfPizzasForDicount) {
            Optional<BigDecimal> max = pizzas.stream().map(i -> i.getPrice()).max(Comparator.naturalOrder());
            if (max.isPresent()) {
                BigDecimal discountAmount = max.get().multiply(discountMultiplier);
                return discountAmount;
            }
        }
        return new BigDecimal(0);
    }


    public BigDecimal calculateTotalPriceWithDiscount() {
        return calculateTotalPrice().subtract(calculateDiscountForFourPizzas())
                .subtract(calculateDiscountFromCummulativeCard());
    }

    public BigDecimal calculateTotalPrice() {
        return pizzas.stream().map(j -> j.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public BigDecimal calculateDiscountFromCummulativeCard() {
        PizzaCard card = customer.getCard();
        BigDecimal cummulativeCardDiscount = CUMMULATIVE_CARD_DISCOUNT_COEF.multiply(card.getBalance());
        BigDecimal totalOrderDiscount = TOTAL_ORDER_DISCOUNT.multiply(calculateTotalPrice());
        if (cummulativeCardDiscount.compareTo(totalOrderDiscount) == 1) {
            return totalOrderDiscount;
        } else return cummulativeCardDiscount;
    }

    public void addAdditionalPizzas(List<Pizza> pizzasListById) {
        pizzas.addAll(pizzasListById);
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pizzas=" + pizzas +
                ", customer=" + customer +
                '}';
    }
}
