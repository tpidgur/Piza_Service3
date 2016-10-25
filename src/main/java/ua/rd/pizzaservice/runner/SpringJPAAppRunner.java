package ua.rd.pizzaservice.runner;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.domain.PizzaOrder;
import ua.rd.pizzaservice.repository.JPAPizzaRepository;
import ua.rd.pizzaservice.repository.PizzaRepository;
import ua.rd.pizzaservice.services.OrderService;

import java.math.BigDecimal;
import java.util.Arrays;

public class SpringJPAAppRunner {
    public static void main(String[] args) {

        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext
                (new String[]{"appContext.xml"}, repoContext);


        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));
        PizzaRepository pizzaRepository=(PizzaRepository) appContext.getBean("pizzaRepository");
        Pizza pizza =new Pizza();
        pizza.setName("Sea");
        pizza.setType(Pizza.PizzaType.SEA);
        pizza.setPrice(new BigDecimal(10));
        pizza=pizzaRepository.save(pizza);
        System.out.println(pizza);

//        OrderService orderService = appContext.getBean("simpleOrderService", OrderService.class);
//
//        PizzaOrder order = orderService.placeNewOrder(null, new Long(1), new Long(2), new Long(3));
//        order.toString();
//        System.out.println(order);
//
//        System.out.println(orderService.getClass());
//

        repoContext.close();
        appContext.close();


    }
}
