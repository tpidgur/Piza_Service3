package ua.rd.pizzaservice.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="customers")
public class Customer implements Serializable {
    @TableGenerator(name = "Customer_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Customer_Gen")
    private Long id;

    private String name;
    private String address;

   @OneToOne
   @JoinColumn(name="PCard_ID")
    private PizzaCard card;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PizzaCard getCard() {
        return card;
    }

    public void setCard(PizzaCard card) {
        this.card = card;
    }
}
