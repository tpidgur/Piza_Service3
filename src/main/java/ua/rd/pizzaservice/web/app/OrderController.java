package ua.rd.pizzaservice.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.rd.pizzaservice.services.OrderService;


@Controller
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
    public String updateCustomer(Model model, @PathVariable("orderId") Long orderId) {
        System.out.println("===updateOrder====");
        model.addAttribute("order", orderService.find(orderId));
        return "order";
    }
}
