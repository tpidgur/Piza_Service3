package ua.rd.pizzaservice.runner;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizzaservice.domain.*;
import ua.rd.pizzaservice.repository.PizzaRepository;
import ua.rd.pizzaservice.services.OrderService;
import ua.rd.pizzaservice.services.PizzaService;

import java.math.BigDecimal;
import java.util.Arrays;

public class SpringJPAAppRunner {
    public static void main(String[] args) {

        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext
                (new String[]{"appContext.xml"}, repoContext);

//
//        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));
 //     PizzaRepository pizzaRepository=(PizzaRepository) appContext.getBean("pizzaRepository");
//
//        Pizza p1 = new Pizza("Sea Pizza", new BigDecimal(1), Pizza.PizzaType.SEA);
//        Pizza p2 = new Pizza("New York Style Pizza", new BigDecimal(1), Pizza.PizzaType.MEAT);
//        Pizza p3 = new Pizza("New Pizza", new BigDecimal(1), Pizza.PizzaType.VEGETERIAN);
//        pizzaRepository.save(p1);
//        pizzaRepository.save(p2);
//        pizzaRepository.save(p3);

        OrderService orderService = appContext.getBean("simpleOrderService", OrderService.class);
        Customer customer = createCustomer();
        customer.setCard(createPizzaCard());

        Order order = orderService.placeNewOrder(customer, 1L, 2L, 3L,3L);



        repoContext.close();
        appContext.close();

    }

    private static PizzaCard createPizzaCard() {
        return new PizzaCard();
    }

    private static Customer createCustomer() {
      return new  Customer("Iren",new Address("c.Kiev,Lomonosova 23,fl.28"),new PizzaCard());
    }
}
