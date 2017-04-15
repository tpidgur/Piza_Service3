package ua.rd.pizzaservice.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@NamedQueries({
        @NamedQuery(name = "Address.findByAddress", query = "select a from Address a where a.address=:address"),
        @NamedQuery(name = "Address.delete", query = "delete  from Address a where a.addressId=:id")
})
public class Address implements Serializable {
    @TableGenerator(name = "Address_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Address_Gen")
    private Long addressId;
    private String address;


    public Address() {//TODO do we really need 2 constructors?
    }

    public Address(String address) {
        this.address = address;
    }

}
