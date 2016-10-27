package ua.rd.pizzaservice.repository;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.Address;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.domain.PizzaCard;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JPACustomerRepositoryITest extends RepositoryTestConfig {
    @Autowired
    private CustomerRepository customerRepository;

    @After
    public void intialTune() {
        customerRepository.delete();
    }

    @Test
    public void findAllTest() {
        List<Customer> expected = initializeTwoCustomers();
        List<Customer> actual = customerRepository.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void findAllByNameTest() {
        initializeTwoCustomers();
       // Customer customer = Arrays.asList(initializeCustomer());
    }

    @Test
    public void findTest() {
        Customer expected = initializeCustomer();
        Customer actual = customerRepository.find(expected.getId());
        assertEquals(expected, actual);

    }


    @Test
    public void saveTest() {
        Customer customer = initializeCustomer();
        assertNotNull(customer.getId());

    }

    private Customer initializeCustomer() {
        return customerRepository.save(new Customer("Ivanov Ivan",
                new Address("c.Kiev, Lomonosova 26, fl.23"), new PizzaCard()));
    }

    private List<Customer> initializeTwoCustomers() {
        Customer customer1 = customerRepository.save(new Customer("Kruk Oleg",
                new Address("c.Lutsk, Gromova 50, fl.40"), new PizzaCard()));
        Customer customer2 = customerRepository.save(new Customer("Idler Iren",
                new Address("c.Kiev, Rynok sq. 30, fl.1"), new PizzaCard()));
        return Arrays.asList(customer1, customer2);
    }

}