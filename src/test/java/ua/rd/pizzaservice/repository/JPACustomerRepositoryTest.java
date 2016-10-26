package ua.rd.pizzaservice.repository;

import org.junit.Test;
import ua.rd.pizzaservice.domain.Customer;

import static org.junit.Assert.*;

public class JPACustomerRepositoryTest extends RepositoryTestConfig {
    @Test
    public void findAllTest() {
        Customer customer1=new Customer("Ivanov Ivan");
    }

    @Test
    public void findAllByNameTest() {

    }

    @Test
    public void findTest() {

    }

    @Test
    public void saveTest() {

    }

}