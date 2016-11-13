package ua.rd.pizzaservice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "customers")
@NamedQueries({
        @NamedQuery(name = "Customer.findAll", query = "SELECT c from Customer c"),
        @NamedQuery(name = "Customer.findByName", query = "SELECT c from Customer c WHERE c.name=:name"),
        @NamedQuery(name = "Customer.updateName", query = "UPDATE Customer c SET c.name='Tania'"),
       // WHERE c.customerId=:id :newName
        @NamedQuery(name = "Customer.deleteAll", query = "delete  from Customer с")
})

@Data
@EqualsAndHashCode(of = {"customerId", "name"})
public class Customer extends ResourceSupport implements Serializable {
    @TableGenerator(name = "Customer_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Customer_Gen")

    private Long customerId;

    @Column(nullable = false)

    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "card_id")
    private PizzaCard card;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, Address address, PizzaCard card) {
        this.name = name;
        this.address = address;
        this.card = card;
    }

    public void createNewCardIfNotExist() {
        if (!hasCard()) {
            createNewCard();
        }
    }

    private boolean hasCard() {
        return card != null;
    }

    private void createNewCard() {
        card = new PizzaCard();
    }

}
