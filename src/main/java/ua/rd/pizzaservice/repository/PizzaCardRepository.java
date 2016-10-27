package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.PizzaCard;

import java.util.List;

public interface PizzaCardRepository {

    PizzaCard find(Long id);

    List<PizzaCard> findAll();

    PizzaCard save(PizzaCard card);

    void delete();
}
