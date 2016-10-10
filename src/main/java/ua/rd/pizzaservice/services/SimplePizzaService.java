package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.infrastructure.BenchMark;
import ua.rd.pizzaservice.infrastructure.InitialContext;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.PizzaRepo;

public class SimplePizzaService implements PizzaService {
    private PizzaRepo pizzaRepo;//=new InMemoryPizzaRepository();

    public SimplePizzaService(PizzaRepo pizzaRepo) {
//        InitialContext context = new InitialContext();
//        this.pizzaRepo = context.getInstance("PizzaRepo");
        this.pizzaRepo = pizzaRepo;
    }
@BenchMark(false)
    public Pizza find(Long id) {
        return pizzaRepo.find(id);
    }
}
