package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.PizzaCard;

import java.util.List;

public interface PizzaCardRepository {
    List<PizzaCard> findAll();

    List<PizzaCard> findAllByCustomer(Customer customer);

    PizzaCard find(Long id);

    PizzaCard save(PizzaCard card);
}
