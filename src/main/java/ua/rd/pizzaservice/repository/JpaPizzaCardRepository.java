package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.PizzaCard;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("cardRepository")
public class JpaPizzaCardRepository implements PizzaCardRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public PizzaCard find(Long id) {
        return em.find(PizzaCard.class, id);
    }


    @Override
    public List<PizzaCard> findAll() {
        return em.createNamedQuery("PizzaCard.findAll", PizzaCard.class).getResultList();
    }

    @Transactional
    @Override
    public PizzaCard save(PizzaCard card) {
        return em.merge(card);
    }

    @Override
    public void delete() {
        em.createNamedQuery("PizzaCard.deleteAll").executeUpdate();
    }
}
