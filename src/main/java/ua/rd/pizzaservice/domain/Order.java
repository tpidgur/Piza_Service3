package ua.rd.pizzaservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Component
@Scope("prototype")
@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "Order.find", query = "delete from Order o WHERE o.orderId=:orderId"),
        @NamedQuery(name = "Order.findAll", query = "SELECT o from Order o"),
        @NamedQuery(name = "Order.findAllByCustomer", query = "SELECT o from Order o  where o.customer=:customer"),
        @NamedQuery(name = "Order.deleteAll", query = "delete  from Order o")
})
@Getter
@Setter //TODO do we really need setters here as well as lombok?
public class Order extends ResourceSupport implements Serializable {
    @TableGenerator(name = "Order_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Order_Gen")
    private Long orderId;

    @ElementCollection(fetch = FetchType.EAGER) //TODO do we really need eager here?
    @CollectionTable(name = "pizzasAmount")
    @MapKeyJoinColumn(name = "pizzaId", referencedColumnName = "pizzaId")
    @Column(name = "quantity")
    @BatchSize(size = 5)
    private Map<Pizza, Integer> pizzas = new HashMap<>();

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Column(name = "date")
    private LocalDate date = LocalDate.now();

    @OneToOne
    private Address address;

    public Order() { //TODO: do we really need two constructors here?
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
                        entry -> entry.getKey(),
                        entry -> entry.getValue(),
                        Integer::sum));
    }

    public void removePizza(Pizza pizza) {
        pizzas.put(pizza, pizzas.get(pizza) - 1);
    }

    public int getAmountOfPizzas() {
        return pizzas.values().stream().reduce(0, Integer::sum);
    }

    public BigDecimal calculateTotalPrice() {
        return pizzas.keySet().stream()
                .map(s -> s.getPrice().multiply(new BigDecimal(pizzas.get(s))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override //TODO: either use Lombok or explicit method declaration
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != null ? !orderId.equals(order.orderId) : order.orderId != null) return false;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) return false;
        return status == order.status;

    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override //TODO: do we really need to override toString?
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", pizzas=" + pizzas +
                ", customer=" + customer +
                ", status=" + status +
                ", date=" + date +
                ", address=" + address +
                '}';
    }
}
