package ua.rd.pizzaservice.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import java.math.BigDecimal;

@Component
@Scope("prototype")
public class PizzaCard {
    private long id;
    private BigDecimal balance = new BigDecimal(0);
    private static long counter;

    public PizzaCard() {
        id = counter++;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
