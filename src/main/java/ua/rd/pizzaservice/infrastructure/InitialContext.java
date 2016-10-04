package ua.rd.pizzaservice.IntialContext;

import ua.rd.pizzaservice.repository.InMemoryPizzaRepository;
import ua.rd.pizzaservice.repository.PizzaRepo;

public class InitialContext {
    public PizzaRepo getInstance(String type) {
        return new InMemoryPizzaRepository();
    }


}
