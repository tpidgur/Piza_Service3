package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Address;
import ua.rd.pizzaservice.domain.Pizza;

import java.util.List;

public interface AddressService {
    Address find(Long id);

    Address findByAddress(String address);

    Address save(Address address);

    void delete(Long id);
}
