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
import ua.rd.pizzaservice.domain.*;
import ua.rd.pizzaservice.services.CustomerService;
import ua.rd.pizzaservice.services.OrderService;
import ua.rd.pizzaservice.services.PizzaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Secured("IS_AUTHENTICATED_FULLY")
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private OrderService orderService;

    @RequestMapping
    public ModelAndView findAll(ModelAndView modelAndView) {
        System.out.println("===findAllOrders====");
        modelAndView.setViewName("orders");
        modelAndView.addObject("orderList", orderService.findAll());
        return modelAndView;
    }

//    @RequestMapping(value = "/{orderId}/edit", method = RequestMethod.POST)
//    public String update(Model model, @PathVariable("orderId") Long orderId) {
//        System.out.println("===updateOrder====");
//        model.addAttribute("order", orderService.find(orderId));
//        return "order";
//    }


    @RequestMapping(value = "/order/create", method = RequestMethod.GET)
    public String create(Model model) {
        System.out.println("===createOrder====");
        Order order = new Order();
        model.addAttribute("order", order);
        return "order";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("orderHolder") OrderHolder holder,
//            @ModelAttribute Customer customer,
//                       @ModelAttribute Address address,
                       Model model) {
        System.out.println("===saveOrder====" + holder);
        System.out.println("===saveOrder====" + orderService.convertOrderHolderToOrder(holder));
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

    @RequestMapping(value = "/{orderId}/edit", method = RequestMethod.POST)
    public String update(Model model, @PathVariable("orderId") Long orderId) {
        System.out.println("===updateOrder====");
        model.addAttribute("order", orderService.find(orderId));
        model.addAttribute("customers",customerService.findAll());
        return "order";
    }

//    private OrderHolder convertOrderToOrderHolder(Order order) {
//        OrderHolder holder = new OrderHolder();
//        holder.setOrderId(order.getOrderId().toString());
//        holder.setCustomerId(order.getCustomer().getCustomerId().toString());
//        holder.setAddressId(order.getAddress().getAddressId().toString());
//        holder.setDate(order.getDate().toString());
//        holder.setStatus(order.getStatus().toString());
//        return holder;
//    }




    protected Map<String, String> convertPizzaMapInStringIdMap(Map<Pizza, Integer> pizzaIds) {
        Map<String, String> pizzas = new HashMap<>();
        pizzaIds.forEach((pizza, amount) -> pizzas.put(pizza.getPizzaId().toString(),
                amount.toString()));
        return pizzas;
    }
//    protected Map<Pizza, Integer> convertIdMapInPizzaMap(Map<String, String> pizzaIds) {
//        Map<Pizza, Integer> pizzas = new HashMap<>();
//        pizzaIds.forEach((s, amount) -> pizzas.put(pizzaService.find(Long.valueOf(s)),
//                Integer.valueOf(amount)));
//        return pizzas;
//    }
}
