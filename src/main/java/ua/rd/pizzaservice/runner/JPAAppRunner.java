package ua.rd.pizzaservice.runner;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.domain.PizzaCard;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class JPAAppRunner {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();

        Pizza pizza = createNewPizza();
        PizzaCard card=createPizzaCard();
        Customer customer=createCustomer();
        customer.setCard(card);


        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();

        em.persist(pizza);
        em.persist(card);
        em.persist(customer);

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

    private static Pizza createNewPizza() {
        return new Pizza("New York Style Pizza", new BigDecimal(1), Pizza.PizzaType.MEAT);
    }
}
