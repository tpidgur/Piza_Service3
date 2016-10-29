package ua.rd.pizzaservice.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


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

    //    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "PizzasList",
//            joinColumns = @JoinColumn(name = "Order_ID"),
//            inverseJoinColumns = @JoinColumn(name = "Pizza_ID"))
//    private List<Pizza> pizzas;

    @ElementCollection
    @CollectionTable(name = "pizzasAmount")
    @MapKeyJoinColumn(name = "pizza_id", referencedColumnName = "id")
    @Column(name = "quantity")
    private Map<Pizza, Integer> pizzas = new HashMap<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "Customer_ID")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "date")
    private LocalDate date;
    @OneToOne
    private Address address;

    public enum Status {
        NEW, IN_PROGRESS, CANCELLED, DONE
    }

    public Order() {
    }

//    public Order(Customer customer, List<Pizza> pizzas) {
//        this.pizzas = pizzas;
//        this.customer = customer;
//        this.status = Status.NEW;
//
//    }


    public Order(Map<Pizza, Integer> pizzas, Customer customer) {
        this.pizzas = pizzas;
        this.customer = customer;
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal result=BigDecimal.ZERO;
        Iterator<Map.Entry<Pizza,Integer>> itr=pizzas.entrySet().iterator();
        while (itr.hasNext()){
            Map.Entry<Pizza,Integer> entry=itr.next();
            result= result.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
        }
       return result;
//        return pizzas.stream().map(j -> j.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


//    public void addAdditionalPizzas(List<Pizza> pizzasListById) {
//        pizzas.addAll(pizzasListById);
//    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

//    public List<Pizza> getPizzas() {
//        return pizzas;
//    }
//
//    public void setPizzas(List<Pizza> pizzas) {
//        this.pizzas = pizzas;
//    }

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
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pizzas=" + pizzas +
                ", customer=" + customer +
                '}';
    }
}
