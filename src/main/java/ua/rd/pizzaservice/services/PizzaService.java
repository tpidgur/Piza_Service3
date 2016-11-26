package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Pizza;

import java.util.List;

public interface PizzaService {
   Pizza find(Long id);
   Pizza save(Pizza pizza);
   List<Pizza> findAll();
   void delete(Long id);
}
