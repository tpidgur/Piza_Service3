package ua.rd.pizzaservice.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
//@Scope("prototype")
@Table(name = "pizzas")
@NamedQueries({
        @NamedQuery(name = "Pizza.findAll", query = "SELECT p FROM Pizza p"),
        @NamedQuery(name = "Pizza.findAllByType", query = "SELECT p FROM Pizza p WHERE p.type=:type"),
        @NamedQuery(name = "Pizza.deleteAll", query = "delete  from Pizza p")
})
public class Pizza implements Serializable {
    @TableGenerator(name = "Pizza_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Pizza_Gen")
    private Long id;

    private String name;
    @Column(precision=1, scale =0)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        if (name != null ? !name.equals(pizza.name) : pizza.name != null) return false;
        if (price != null ? !price.equals(pizza.price) : pizza.price != null) return false;
        return type == pizza.type;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
