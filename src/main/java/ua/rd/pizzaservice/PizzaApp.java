package ua.rd.pizzaservice;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.services.SimpleOrderService;

public class PizzaApp {
    public static void main(String[] args) {
        Customer customer = null;
        Order order;
        SimpleOrderService orderService = new SimpleOrderService();
        order = orderService.placeNewOrder(customer, new Long(1), new Long(2), new Long(3));
        System.out.println(order);
    }

}
