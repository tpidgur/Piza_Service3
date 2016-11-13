package ua.rd.pizzaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.repository.CustomerRepository;

import java.util.List;

@Service
public class SimpleCustomerService implements CustomerService {
    private CustomerRepository customerRepo;

    @Autowired
    public SimpleCustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer find(Long id) {
        return null;
    }

    @Override
    public List<Customer> findAllByName(String name) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }
}
