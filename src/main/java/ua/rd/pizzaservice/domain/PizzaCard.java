package ua.rd.pizzaservice.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import java.math.BigDecimal;

@Entity
@Component
@Scope("prototype")
public class PizzaCard {
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
}
