package ua.rd.pizzaservice.runner;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.infrastructure.ApplicationContext;
import ua.rd.pizzaservice.infrastructure.Context;
import ua.rd.pizzaservice.infrastructure.JavaConfig;
import ua.rd.pizzaservice.repository.PizzaRepository;
import ua.rd.pizzaservice.services.OrderService;

import java.lang.reflect.InvocationTargetException;

public class PizzaApp {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Customer customer = null;

        Context context = new ApplicationContext(new JavaConfig());
        PizzaRepository pizzaRepo = context.getBean("pizzaRepository");
        System.out.println(pizzaRepo.find(new Long(1)));

        OrderService orderService = context.getBean("orderService");
            Order order = orderService.placeNewOrder(customer, new Long(1), new Long(2), new Long(3));


    }

}
