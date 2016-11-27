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
import ua.rd.pizzaservice.domain.Address;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Secured("IS_AUTHENTICATED_FULLY")
@RequestMapping("/orders")
public class OrderController {



    @Autowired
    private OrderService orderService;

    @RequestMapping
    public ModelAndView findAll(ModelAndView modelAndView) {
        System.out.println("===findAllOrders====");
        modelAndView.setViewName("orders");
        modelAndView.addObject("orderList", orderService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/{orderId}/edit", method = RequestMethod.POST)
    public String update(Model model, @PathVariable("orderId") Long orderId) {
        System.out.println("===updateOrder====");
        model.addAttribute("order", orderService.find(orderId));
        return "order";
    }


    @RequestMapping(value = "/order/create", method = RequestMethod.GET)
    public String create(Model model) {
        System.out.println("===createOrder====");
        Order order = new Order();
        model.addAttribute("order", order);
        return "order";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("myorder")Order order,
//            @ModelAttribute Customer customer,
//                       @ModelAttribute Address address,
                       Model model) {
        System.out.println("===saveOrder===="+order);
        //orderService.placeNewOrder(order);
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "redirect:/app/orders";
    }


    @RequestMapping(value = "/savemap", method = RequestMethod.POST)
    public String saveMap(@ModelAttribute("mymap") Order order) {
        System.out.println("PIZZAS==" + order);
        return "redirect:/app/orders";
    }
}
