package ua.rd.pizzaservice.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@Scope("prototype")
@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT o from Order o"),
        @NamedQuery(name = "Order.findAllByCustomer", query = "SELECT o from Order o where o.customer=:customer"),
        @NamedQuery(name = "Order.deleteAll", query = "delete  from Order o")
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pizzasAmount")
    @MapKeyJoinColumn(name = "pizza_id", referencedColumnName = "id")
    @Column(name = "quantity")
    private Map<Pizza, Integer> pizzas = new HashMap<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "Customer_ID")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;
    @Column(name = "date")
    private LocalDate date = LocalDate.now();
    @OneToOne
    private Address address;

    public Order() {
    }


    public Order(Map<Pizza, Integer> pizzas, Customer customer) {
        this.pizzas = pizzas;
        this.customer = customer;
    }


    public enum Status {
        NEW, IN_PROGRESS, CANCELLED, DONE
    }

    public void addPizzas(Map<Pizza, Integer> additionalPizzas) {
        pizzas = Stream.concat(pizzas.entrySet().stream(), additionalPizzas.entrySet().stream())
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),//The key
                        entry -> entry.getValue(),//The value
                        //  The merger
                        Integer::sum
                ));
    }

    public void removePizza(Pizza pizza) {
        pizzas.put(pizza, pizzas.get(pizza) - 1);
    }

    public int getAmountOfPizzas() {
        return pizzas.values().stream().reduce(0, Integer::sum);
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal result = BigDecimal.ZERO;
        Iterator<Map.Entry<Pizza, Integer>> itr = pizzas.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Pizza, Integer> entry = itr.next();
            result = result.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
        }
        return result;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Map<Pizza, Integer> getPizzas() {
        return pizzas;
    }

    public void setPizzas(Map<Pizza, Integer> pizzas) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (pizzas != null ? !pizzas.equals(order.pizzas) : order.pizzas != null) return false;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) return false;
        if (status != order.status) return false;
        if (date != null ? !date.equals(order.date) : order.date != null) return false;
        return address != null ? address.equals(order.address) : order.address == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pizzas != null ? pizzas.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pizzas=" + pizzas +
                ", customer=" + customer +
                ", status=" + status +
                ", date=" + date +
                ", address=" + address +
                '}';
    }

}
