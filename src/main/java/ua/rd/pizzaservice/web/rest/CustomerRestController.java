package ua.rd.pizzaservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.CustomerService;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "customer/{customerID}", method = RequestMethod.GET)
    public ResponseEntity<Customer> findCustomerById(@PathVariable("customerID") Long customerID) {
        Customer customer = customerService.find(customerID);
        if (customer == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        Link link = linkTo(methodOn(CustomerRestController.class).findCustomerById(customerID)).withSelfRel();
        customer.add(link);
        return new ResponseEntity<Customer>(customer, HttpStatus.FOUND);
    }

    @RequestMapping(value = "customers/", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customers = customerService.findAll();
        if (customers == null) {
            return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> addCustomer(@RequestBody Customer customer,
                                            UriComponentsBuilder builder) {
        Customer customer1 = customerService.save(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/customer/{customerID}")
                        .buildAndExpand(customer1.getCustomerId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
