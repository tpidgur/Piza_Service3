package ua.rd.pizzaservice.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Component
//@Scope("prototype")
@Table(name ="pizzaCards")
@NamedQueries({
        @NamedQuery(name = "PizzaCard.findAll", query = "SELECT pc from PizzaCard pc"),
        @NamedQuery(name = "PizzaCard.deleteAll", query = "delete  from PizzaCard pс")})
public class PizzaCard implements Serializable {
    @TableGenerator(name = "Pizza_Card_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Pizza_Card_Gen")
    private Long id;

    private BigDecimal balance = new BigDecimal(0);


    public PizzaCard() {

    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PizzaCard{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
