package ua.rd.pizzaservice.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.PizzaCard;
import ua.rd.pizzaservice.services.CustomerService;

import java.util.List;


@Controller
@RequestMapping("/customers")
@Secured("IS_AUTHENTICATED_FULLY")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping
    public ModelAndView findAll(ModelAndView modelAndView) {
        System.out.println("===findAll====");
        modelAndView.setViewName("customers");
        modelAndView.addObject("customerList", customerService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{customerId}", method = RequestMethod.POST)
    public String updateCustomer(Model model, @PathVariable("customerId") Long customerId) {
        System.out.println("===updateCustomer====");
        model.addAttribute("customer", customerService.find(customerId));
        return "customer";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCustomer(@ModelAttribute PizzaCard card, @ModelAttribute Customer customer, Model model) {
        if (card != null) {
            customer.setCard(card);
        }
        System.out.println("===saveCustomer====" + customer);
        customerService.save(customer);
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "redirect:/app/customers";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createCustomer(Model model) {
        System.out.println("===createCustomer====");
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer";
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public String showCustomerProfile(@PathVariable("customerId") Long customerId, Model model) {
        model.addAttribute("customer", customerService.find(customerId));
        return "profile";
    }

}
