package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Pizza;

import java.util.List;

public interface PizzaRepo {
    public List<Pizza> getPizzas();

    Pizza find(Long id);
}
