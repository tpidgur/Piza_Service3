package ua.rd.pizzaservice.runner;

import ua.rd.pizzaservice.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class JPAAppRunner {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();
      //  Pizza pizza = new Pizza("New York Style Pizza", new BigDecimal(1), Pizza.PizzaType.MEAT);
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
       // em.persist(pizza);
        entityTransaction.commit();
        em.clear();

        Pizza p = em.find(Pizza.class, new Long(0));
       // System.out.println(p == pizza);
        System.out.println(p);

        em.close();
        emf.close();
    }
}
