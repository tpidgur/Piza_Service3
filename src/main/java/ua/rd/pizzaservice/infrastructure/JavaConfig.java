package ua.rd.pizzaservice.infrastructure;

import ua.rd.pizzaservice.repository.InMemoryOrderRepository;
import ua.rd.pizzaservice.repository.InMemoryPizzaRepository;
import ua.rd.pizzaservice.repository.PizzaRepo;
import ua.rd.pizzaservice.services.SimpleOrderService;
import ua.rd.pizzaservice.services.SimplePizzaService;

import java.util.HashMap;
import java.util.Map;

public class JavaConfig implements Config {

    private Map<String, Class <?>> classes=new HashMap <>();
    {
        classes.put("pizzaRepo", InMemoryPizzaRepository.class);
        classes.put("orderService", SimpleOrderService.class);
        classes.put("orderRepo", InMemoryOrderRepository.class);
        classes.put("pizzaService", SimplePizzaService.class);
    }
    @Override
    public Class<?> getImpl(String name) {
        return classes.get(name);
    }
}
