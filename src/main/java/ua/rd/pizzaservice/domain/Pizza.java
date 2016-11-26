package ua.rd.pizzaservice.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "pizzas")
@NamedQueries({
        @NamedQuery(name = "Pizza.findAll", query = "SELECT p FROM Pizza p"),
        @NamedQuery(name = "Pizza.findAllByType", query = "SELECT p FROM Pizza p WHERE p.type=:type"),
        @NamedQuery(name = "Pizza.delete", query = "delete from Pizza p WHERE p.pizzaId=:pizzaId"),
        @NamedQuery(name = "Pizza.deleteAll", query = "delete  from Pizza p")
})
@Data
public class Pizza extends ResourceSupport implements Serializable {
    @TableGenerator(name = "Pizza_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Pizza_Gen")
    private Long pizzaId;

    private String name;
    //@Column(precision=1, scale =0)
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


}
