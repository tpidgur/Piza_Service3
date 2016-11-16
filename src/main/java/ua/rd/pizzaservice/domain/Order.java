package ua.rd.pizzaservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.persistence.FetchType.EAGER;


@Component
@Scope("prototype")
@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT o from Order o"),
        @NamedQuery(name = "Order.findAllByCustomer", query = "SELECT o from Order o  where o.customer=:customer"),
        @NamedQuery(name = "Order.deleteAll", query = "delete  from Order o")
})
@Getter
@Setter
public class Order  extends ResourceSupport implements Serializable {
    @TableGenerator(name = "Order_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Order_Gen")

    private Long orderId;
   // @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
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

    @Override
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
}
