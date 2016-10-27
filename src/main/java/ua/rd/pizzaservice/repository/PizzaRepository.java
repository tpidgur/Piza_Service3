package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Pizza;

import java.util.List;

public interface PizzaRepository {
    Pizza find(Long id);

    List<Pizza> findAllByType(Pizza.PizzaType type);

    List<Pizza> findAll();

    Pizza save(Pizza pizza);
}
