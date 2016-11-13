package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.PizzaCard;

import java.util.List;

public interface PizzaCardService {
    PizzaCard find(Long id);

    List<PizzaCard> findAll();

    PizzaCard save(PizzaCard card);
}
