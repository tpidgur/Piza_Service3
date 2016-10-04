package ua.rd.pizzaservice;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.infrastructure.ApplicationContext;
import ua.rd.pizzaservice.infrastructure.Context;
import ua.rd.pizzaservice.infrastructure.JavaConfig;
import ua.rd.pizzaservice.repository.PizzaRepo;
import ua.rd.pizzaservice.services.OrderService;
import ua.rd.pizzaservice.services.PizzaService;
import ua.rd.pizzaservice.services.SimpleOrderService;
import ua.rd.pizzaservice.services.SimplePizzaService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PizzaApp {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
  Customer customer = null;
//        Order order;
//        SimpleOrderService orderService = new SimpleOrderService();
//        order = orderService.placeNewOrder(customer, new Long(1), new Long(2), new Long(3));
//        System.out.println(order);
//        PizzaService pizzaService=new SimplePizzaService();
//
//        System.out.println(pizzaService.find(new Long(1)));
//        List<?> intList = new ArrayList<Integer>();
//        intList = new ArrayList<String>();

        Context context=new ApplicationContext(new JavaConfig());
        PizzaRepo pizzaRepo=context.getBean("pizzaRepo");
        System.out.println(pizzaRepo.find(new Long(1)));

        OrderService orderService=context.getBean("orderService");
       Order order=orderService.placeNewOrder(customer,new Long(1), new Long(2), new Long(3));


    }

}
