package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizzaservice.domain.Pizza;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("pizzaRepository")
public class JpaPizzaRepository implements PizzaRepository {
    @PersistenceContext
    private EntityManager em;


    @Override
    public Pizza find(Long id) {
        return em.find(Pizza.class, id);
    }

    @Override
    public List<Pizza> findAllByType(Pizza.PizzaType type) {
        return em.createNamedQuery("Pizza.findAllByType", Pizza.class)
                .setParameter("type", type).getResultList();
    }

    @Override
    public List<Pizza> findAll() {
        return em.createNamedQuery("Pizza.findAll", Pizza.class).getResultList();
    }

    @Transactional
    @Override
    public Pizza save(Pizza pizza) {
        if (pizza.getId() == null) {
            em.persist(pizza);
        } else {
            pizza = em.merge(pizza);
        }
        return pizza;
    }

    @Override
    public void delete() {
        em.createNamedQuery("Pizza.deleteAll").executeUpdate();
    }
}
