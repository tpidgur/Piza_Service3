package ua.rd.pizzaservice.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import java.io.Serializable;

@Entity
@Data
public class Address implements Serializable {
    @TableGenerator(name = "Address_Gen",
            table = "ID_GEN",
            pkColumnName = "Gen_name",
            valueColumnName = "Gen_val",
            initialValue = 0,
            allocationSize = 50)
    @Id
    @GeneratedValue(generator = "Address_Gen")
    private Long id;

    private String address;

    public Address() {
    }

    public Address(String address) {
        this.address = address;
    }


}
