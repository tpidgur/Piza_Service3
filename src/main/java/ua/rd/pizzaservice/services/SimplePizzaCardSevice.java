package ua.rd.pizzaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.pizzaservice.domain.PizzaCard;
import ua.rd.pizzaservice.repository.PizzaCardRepository;

import java.util.List;

@Service
public class SimplePizzaCardSevice implements PizzaCardService {
    private PizzaCardRepository pizzaCardRepo;

    @Autowired
    public SimplePizzaCardSevice(PizzaCardRepository pizzaCardRepo) {
        this.pizzaCardRepo = pizzaCardRepo;
    }

    @Override
    public PizzaCard find(Long id) {
        return pizzaCardRepo.find(id);
    }

    @Override
    public List<PizzaCard> findAll() {
        return pizzaCardRepo.findAll();
    }

    @Override
    public PizzaCard save(PizzaCard card) {
        return pizzaCardRepo.save(card);
    }
}
