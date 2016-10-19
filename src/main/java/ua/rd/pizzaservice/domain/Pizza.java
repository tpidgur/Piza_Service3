package ua.rd.pizzaservice.domain;


import org.springframework.context.annotation.Scope;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Scope("prototype")
public class Pizza implements Serializable {
    @TableGenerator(name = "Pizza_Gen",
            table = "Pizza_ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Pizza_Gen")
    private Long id;

    private String name;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PizzaType type;


    public enum PizzaType {
        VEGETERIAN, SEA, MEAT
    }

    public Pizza() {
    }

    public Pizza(String name, BigDecimal price, PizzaType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PizzaType getType() {
        return type;
    }

    public void setType(PizzaType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
