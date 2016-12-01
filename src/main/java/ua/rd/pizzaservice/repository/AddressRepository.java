package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Address;
import ua.rd.pizzaservice.domain.Customer;

import java.util.List;

public interface AddressRepository {
    Address find(Long addressId);

    Address findByAddress(String address);

    Address save(Address address);


    void delete(Long id);
}
