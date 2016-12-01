package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.pizzaservice.domain.Address;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("addressRepository")
public class JpaAddressRepository implements AddressRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Address find(Long addressId) {
        return em.find(Address.class, addressId);
    }

    @Override
    public Address findByAddress(String address) {
        return (Address) em.createNamedQuery("Address.findByAddress").setParameter("address", address).getSingleResult();
    }

    @Transactional
    @Override
    public Address save(Address address) {
        return em.merge(address);
    }

    @Override
    public void delete(Long id) {
        em.createNamedQuery("Address.delete").setParameter("id", id).executeUpdate();
    }
}
