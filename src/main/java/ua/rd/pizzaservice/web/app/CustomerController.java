package ua.rd.pizzaservice.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.services.CustomerService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping
    public ModelAndView findAll(ModelAndView modelAndView) {
        System.out.println("===findAll====");
        modelAndView.setViewName("customers");
        modelAndView.addObject("customerList", customerService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/{customerId}/edit", method = RequestMethod.POST)
    public String updateCustomer(Model model, @PathVariable("customerId") Long customerId) {
        System.out.println("===updateCustomer====");
        model.addAttribute("customer", customerService.find(customerId));
        return "customer";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCustomer(@RequestParam("address") String address,
                               @RequestParam("balance") BigDecimal balance,
                               @ModelAttribute Customer customer, Model model) {
        customer.getCard().setBalance(balance);
        customer.getAddress().setAddress(address);
        System.out.println("===saveCustomer====" + customer);
        customerService.save(customer);
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "redirect:/app/customers";
    }

    @RequestMapping(value = "/0/create", method = RequestMethod.GET)
    public String createCustomer(Model model) {
        System.out.println("===createCustomer====");
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer";
    }
}
