package ua.rd.pizzaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.pizzaservice.infrastructure.BenchMark;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.PizzaRepository;

import java.util.List;

@Service
public class SimplePizzaService implements PizzaService {
    private PizzaRepository pizzaRepo;

    @Autowired
    public SimplePizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepo = pizzaRepository;
    }

    @BenchMark(true)
    @Override
    public Pizza find(Long id) {
        return pizzaRepo.find(id);
    }

    @Override
    public Pizza save(Pizza pizza) {
        return pizzaRepo.save(pizza);
    }

    @Override
    public List<Pizza> findAll() {
        return pizzaRepo.findAll();
    }
}
