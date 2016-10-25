package ua.rd.pizzaservice.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Component
@Scope("prototype")
@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT o from Order o"),
        @NamedQuery(name = "Order.findAllByCustomer", query = "SELECT o from Order o where o.customer=:customer")
})
public class Order implements Serializable {
    @TableGenerator(name = "Order_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Order_Gen")
    private Long id;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "PizzasList",
            joinColumns = @JoinColumn(name = "Order_ID"),
            inverseJoinColumns = @JoinColumn(name = "Pizza_ID"))
    private List<Pizza> pizzas;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "Customer_ID")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Status status;


    public enum Status {
        NEW, IN_PROGRESS, CANCELLED, DONE
    }

    public Order() {
    }

    public Order(Customer customer, List<Pizza> pizzas) {
        this.pizzas = pizzas;
        this.customer = customer;
        this.status = Status.NEW;

    }


    public BigDecimal calculateTotalPrice() {
        return pizzas.stream().map(j -> j.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
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
