package ua.rd.pizzaservice.runner;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.services.OrderService;

import java.util.Arrays;

public class SpringAppRunner {
    public static void main(String[] args) {

        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"},
                repoContext);


        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));
        OrderService orderService =  appContext.getBean("simpleOrderService",OrderService.class);
       // Customer customer=appContext.getBean("customer",Customer.class);
        Order order = orderService.placeNewOrder(null, new Long(1), new Long(2), new Long(3));

        System.out.println(order);
        System.out.println(orderService.getClass());


        repoContext.close();
       appContext.close();




    }
}
