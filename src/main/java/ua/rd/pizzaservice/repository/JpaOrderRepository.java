package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("orderRepository")
public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager em;


    @Override
    public Order find(long id) {
        return em.find(Order.class, id);
    }


    @Override
    public List<Order> findAllByCustomer(Customer customer) {
        return em.createNamedQuery("Order.findAllByCustomer", Order.class).setParameter("customer", customer)
                .getResultList();
    }

    @Override
    public List<Order> findAll() {
        return em.createNamedQuery("Order.findAll", Order.class).getResultList();
    }

    @Transactional
    @Override
    public Order save(Order order) {
        return em.merge(order);
    }

    @Override
    public void delete() {
        em.createNamedQuery("Order.deleteAll").executeUpdate();
    }
}
