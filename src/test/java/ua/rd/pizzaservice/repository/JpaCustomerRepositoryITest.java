package ua.rd.pizzaservice.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.Address;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.PizzaCard;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JpaCustomerRepositoryITest extends RepositoryTestConfig {
    public static final String NEW_NAME = "Bloh Kristina";
    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void intialTune() {
        jdbcTemplate.update("DELETE FROM customers");
        jdbcTemplate.update("DELETE FROM pizzacards");
        jdbcTemplate.update("DELETE FROM address");
    }

    @Test
    public void findTest() {
        Customer expected = initializeOneCustomer();
        Customer actual = customerRepository.find(expected.getCustomerId());
        assertEquals(expected, actual);
    }

    @Test
    public void findAllByNameTest() {
        initializeTwoCustomers();
        Customer customer = initializeOneCustomer();
        List<Customer> actual = customerRepository.findAllByName(customer.getName());
        assertEquals(actual, Arrays.asList(customer));
    }

    @Test
    public void findAllTest() {
        List<Customer> expected = initializeTwoCustomers();
        List<Customer> actual = customerRepository.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void updateNameTest() {
        Customer customer = initializeOneCustomer();
        System.out.println(customer);
        customerRepository.updateName(NEW_NAME,customer.getCustomerId());
        System.out.println(customerRepository.find(customer.getCustomerId()));
        //assertThat(customerRepository.find(customer.getCustomerId()).getName(),is(NEW_NAME));
    }

    @Test
    public void saveTest() {
        Customer customer = initializeOneCustomer();
        assertNotNull(customer.getCustomerId());

    }

    private Customer initializeOneCustomer() {
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