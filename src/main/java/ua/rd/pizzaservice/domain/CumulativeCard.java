package ua.rd.pizzaservice.domain;

import java.math.BigDecimal;

public class CumulativeCard {
    private long id;
    private BigDecimal balance;
    private static long counter;

    public CumulativeCard() {
        id = counter++;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
