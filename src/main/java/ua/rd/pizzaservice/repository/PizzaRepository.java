package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Pizza;

import java.util.List;

public interface PizzaRepository {
    List<Pizza> findAll();

    List<Pizza> findAllByType(Pizza.PizzaType type);

    Pizza find(Long id);

    Pizza save(Pizza pizza);
}
