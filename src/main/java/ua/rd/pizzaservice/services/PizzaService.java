package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Pizza;

public interface PizzaService {
   Pizza find(Long id);
   public Pizza save(Pizza pizza);
}
