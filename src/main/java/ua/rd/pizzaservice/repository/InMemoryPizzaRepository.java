package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.PizzaService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class InMemoryPizzaRepository implements PizzaRepo {
    private static List<Pizza> pizzas = new LinkedList<>();

    static {
        pizzas.add(new Pizza("Neapolitan Pizza", new BigDecimal(1), Pizza.PizzaType.MEAT));
        pizzas.add(new Pizza("Chicago Pizza", new BigDecimal(2), Pizza.PizzaType.MEAT));
        pizzas.add(new Pizza("New York Style Pizza", new BigDecimal(3), Pizza.PizzaType.MEAT));
        pizzas.add(new Pizza("Greek Pizza", new BigDecimal(3), Pizza.PizzaType.VEGETERIAN));
        pizzas.add(new Pizza("Sea Pizza", new BigDecimal(3), Pizza.PizzaType.SEA));
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @Override
    public Pizza find(Long id) {
        Iterator<Pizza> iter = pizzas.iterator();
        while (iter.hasNext()) {
            Pizza item = iter.next();
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

}
