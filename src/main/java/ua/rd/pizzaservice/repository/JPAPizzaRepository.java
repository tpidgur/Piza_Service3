package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizzaservice.domain.Pizza;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("pizzaRepository")
public class JPAPizzaRepository implements PizzaRepository {
    @PersistenceContext
    private EntityManager em;

    @Override

    public List<Pizza> findAll() {
        return em.createNamedQuery("Pizza.findAll", Pizza.class).getResultList();
    }

    @Override
    public List<Pizza> findAllByType(Pizza.PizzaType type) {
        return em.createNamedQuery("Pizza.findAllByType", Pizza.class).setParameter("type",type).getResultList();
    }

    @Override
    public Pizza find(Long id) {
        return em.find(Pizza.class, id);
    }

    @Override
    @Transactional
    public Pizza save(Pizza pizza) {
        return em.merge(pizza);

    }
}
