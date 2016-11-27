package ua.rd.pizzaservice.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.services.CustomerService;

@ControllerAdvice
public class CustomerControllerAdvice {
    @Autowired
    private CustomerService customerService;

    @ModelAttribute
    public Customer status(@RequestParam(name = "customerId", required = false) Customer customer) {
        System.out.println("Advice" + customer);
        return customer;
    }
}
