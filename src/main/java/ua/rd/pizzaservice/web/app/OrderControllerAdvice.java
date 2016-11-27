package ua.rd.pizzaservice.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.services.OrderService;
import ua.rd.pizzaservice.services.PizzaService;

@ControllerAdvice
public class OrderControllerAdvice {
    @Autowired
    private OrderService orderService;

    @ModelAttribute
    public Order status(@RequestParam(name = "orderId", required = false) Order order) {
        System.out.println("AdviceOrder" + order);
        return order;
    }
}
