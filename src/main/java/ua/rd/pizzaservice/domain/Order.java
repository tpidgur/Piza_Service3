package ua.rd.pizzaservice.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizzaservice.infrastructure.BenchMark;

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



    public enum Status {
        NEW, IN_PROGRESS, CANCELLED, DONE
    }

    public Order() {
    }

    public Order(Customer customer, List<Pizza> pizzas) {
        this.pizzas = pizzas;
        this.customer = customer;
        this.status = Status.NEW;
        id = counter++;
    }

//@BenchMark
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
