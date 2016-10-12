package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Pizza;

import java.util.List;

public interface PizzaRepository {
    List<Pizza> getPizzas();

    Pizza find(Long id);
}
