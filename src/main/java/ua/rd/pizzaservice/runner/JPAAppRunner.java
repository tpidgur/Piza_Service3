package ua.rd.pizzaservice.runner;

import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.domain.PizzaCard;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Transactional
public class JPAAppRunner {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();

//        Pizza p1 = new Pizza("Sea Pizza", new BigDecimal(1), Pizza.PizzaType.SEA);
//        Pizza p2 = new Pizza("New York Style Pizza", new BigDecimal(1), Pizza.PizzaType.MEAT);
//        Pizza p3 = new Pizza("New Pizza", new BigDecimal(1), Pizza.PizzaType.VEGETERIAN);
        PizzaCard card = createPizzaCard();


        Map<Pizza, Integer> pizzas1 = new HashMap<>();
        pizzas1.put(new Pizza("Bavarian", new BigDecimal(1), Pizza.PizzaType.MEAT), 1);



        Customer customer = createCustomer();
        customer.setCard(card);
        Order order = new Order(pizzas1, new Customer("Ivan"));


        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();

//        em.persist(p1);
//        em.persist(p2);
//        em.persist(p3);
//        em.persist(card);
//        em.persist(customer);
        em.persist(order);

        entityTransaction.commit();
        em.clear();
        em.close();
        emf.close();
    }


    private static PizzaCard createPizzaCard() {
        return new PizzaCard();
    }

    private static Customer createCustomer() {
        return new Customer("Iren");
    }


}
