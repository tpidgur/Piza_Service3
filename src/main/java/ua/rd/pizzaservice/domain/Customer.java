package ua.rd.pizzaservice.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "customers")
@NamedQueries({
        @NamedQuery(name = "Customer.findAll", query = "SELECT c from Customer c"),
        @NamedQuery(name = "Customer.findByName", query = "SELECT c from Customer c WHERE c.name=:name"),
        @NamedQuery(name = "Customer.deleteAll", query = "delete  from Customer с")
})
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

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PizzaCard getCard() {
        return card;
    }

    public void setCard(PizzaCard card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!id.equals(customer.id)) return false;
        return name.equals(customer.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", card=" + card +
                '}';
    }


}
