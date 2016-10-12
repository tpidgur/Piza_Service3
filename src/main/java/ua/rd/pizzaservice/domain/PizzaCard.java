package ua.rd.pizzaservice.domain;

import java.math.BigDecimal;

//Pizza Cumulative card
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
