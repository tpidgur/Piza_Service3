package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.IntialContext.InitialContext;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.InMemoryPizzaRepository;
import ua.rd.pizzaservice.repository.PizzaRepo;


import java.util.Iterator;

public class SimplePizzaService implements PizzaService {
    private PizzaRepo pizzaRepo;//=new InMemoryPizzaRepository();

    public SimplePizzaService() {
        InitialContext context= new InitialContext();
        this.pizzaRepo = context.getInstance("pizzaRepository");
    }

    public  Pizza find (Long id){
      return pizzaRepo.find(id);
  }
}
