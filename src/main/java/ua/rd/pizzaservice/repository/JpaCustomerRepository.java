package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizzaservice.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("customerRepository")
public class JpaCustomerRepository implements CustomerRepository {
    @PersistenceContext
    private EntityManager em;


    @Override
    public Customer find(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> findAllByName(String name) {
        return em.createNamedQuery("Customer.findByName", Customer.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public List<Customer> findAll() {
        return em.createNamedQuery("Customer.findAll", Customer.class)
                .getResultList();
    }


    @Override
    public Customer save(Customer customer) {
        return em.merge(customer);
    }

    @Override
    public void delete() {
        em.createNamedQuery("Customer.deleteAll").executeUpdate();
    }
}
