package ua.rd.pizzaservice.runner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.repository.PizzaRepo;
import ua.rd.pizzaservice.services.OrderService;
import ua.rd.pizzaservice.services.SimpleOrderService;

import java.util.Arrays;

public class SpringAppRunner {
    public static void main(String[] args) {

        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"},
                repoContext);


        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));

//        PizzaRepo pizzaRepo = (PizzaRepo) repoContext.getBean("pizzaRepository");
//        System.out.println(pizzaRepo.find(new Long(1)));





       // OrderService orderService = (OrderService) appContext.getBean("simpleOrderService");
        OrderService orderService =  appContext.getBean("simpleOrderService",OrderService.class);
       // (( SimpleOrderService)orderService).setApplicationContext((ApplicationContext) appContext);
        Order order = orderService.placeNewOrder(null, new Long(1), new Long(2), new Long(3));


        repoContext.close();
        appContext.close();




    }
}
