package ua.rd.pizzaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.pizzaservice.domain.Address;
import ua.rd.pizzaservice.repository.AddressRepository;

@Service
public class SimpleAddressService implements AddressService {
    @Autowired
    private AddressRepository addressRepo;

    @Override
    public Address find(Long id) {
        return addressRepo.find(id);
    }

    @Override
    public Address findByAddress(String address) {
        return addressRepo.findByAddress(address);
    }

    @Override
    public Address save(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public void delete(Long id) {
        addressRepo.delete(id);
    }
}
