package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.PizzaCard;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("cardRepository")
public class JpaCardRepository implements PizzaCardRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PizzaCard> findAll() {
        return em.createNamedQuery("PizzaCard.findAll", PizzaCard.class).getResultList();
    }

    @Override
    public List<PizzaCard> findAllByCustomer(Customer customer) {
        throw new IllegalArgumentException("Not realized yet!");
    }

    @Override
    public PizzaCard find(Long id) {
        return em.find(PizzaCard.class, id);

    }

    @Override
    public PizzaCard save(PizzaCard card) {
        return em.merge(card);
    }
}
