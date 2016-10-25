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

    public List<Pizza> getPizzas() {
        return null;
    }

    @Override
    public Pizza find(Long id) {
        return em.find(Pizza.class, id);
    }

    @Override
    @Transactional
    public Pizza save(Pizza pizza) {
      return   em.merge(pizza);

    }
}
