package ua.rd.pizzaservice.runner;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.domain.PizzaCard;
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


        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));
    //    PizzaRepository pizzaRepository=(PizzaRepository) appContext.getBean("pizzaRepository");




//        Pizza pizza =new Pizza();
//        pizza.setName("Sea");
//        pizza.setType(Pizza.PizzaType.SEA);
//        pizza.setPrice(new BigDecimal(10));
//        pizza=pizzaRepository.save(pizza);
//        Pizza p1 = new Pizza("Sea Pizza", new BigDecimal(1), Pizza.PizzaType.SEA);
//        Pizza p2 = new Pizza("New York Style Pizza", new BigDecimal(1), Pizza.PizzaType.MEAT);
//        Pizza p3 = new Pizza("New Pizza", new BigDecimal(1), Pizza.PizzaType.VEGETERIAN);
//
//        System.out.println(p1+"|||"+p2);



        OrderService orderService = appContext.getBean("simpleOrderService", OrderService.class);
        Customer customer = createCustomer();
        customer.setCard(createPizzaCard());

        Order order = orderService.placeNewOrder(customer, 1L, 52L, 102L);



        repoContext.close();
        appContext.close();
       System.out.println(order);

    }

    private static PizzaCard createPizzaCard() {
        return new PizzaCard();
    }

    private static Customer createCustomer() {
        return new Customer("Iren");
    }
}
