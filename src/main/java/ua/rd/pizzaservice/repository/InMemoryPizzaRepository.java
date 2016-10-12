package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.infrastructure.BenchMark;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryPizzaRepository implements PizzaRepository {
    private List<Pizza> pizzas = new LinkedList<>();

    @PostConstruct
    @BenchMark
    public void init() {
        pizzas.add(new Pizza("Neapolitan Pizza", new BigDecimal(1), Pizza.PizzaType.MEAT));
        pizzas.add(new Pizza("Chicago Pizza", new BigDecimal(2), Pizza.PizzaType.MEAT));
        pizzas.add(new Pizza("New York Style Pizza", new BigDecimal(3), Pizza.PizzaType.MEAT));
        pizzas.add(new Pizza("Greek Pizza", new BigDecimal(3), Pizza.PizzaType.VEGETERIAN));
        pizzas.add(new Pizza("Sea Pizza", new BigDecimal(3), Pizza.PizzaType.SEA));
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @BenchMark
    @Override
    public Pizza find(Long pizzaId) {
        //  return pizzas.stream().filter(e->e.getId().equals(id)).findAny().orElse(null);
        Iterator<Pizza> iter = pizzas.iterator();
        while (iter.hasNext()) {
            Pizza pizza = iter.next();
            if (pizza.getId().equals(pizzaId)) {
                return pizza;
            }
        }
        return null;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

}
