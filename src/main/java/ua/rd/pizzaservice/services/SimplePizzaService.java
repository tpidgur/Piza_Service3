package ua.rd.pizzaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.pizzaservice.infrastructure.BenchMark;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.PizzaRepository;

@Service
public class SimplePizzaService implements PizzaService {
    private PizzaRepository pizzaRepo;//=new InMemoryPizzaRepository();
@Autowired
    public SimplePizzaService(PizzaRepository pizzaRepo) {
//        InitialContext context = new InitialContext();
//        this.pizzaRepo = context.getInstance("PizzaRepository");
        this.pizzaRepo = pizzaRepo;
    }
@BenchMark(false)
    public Pizza find(Long id) {
        return pizzaRepo.find(id);
    }
}
