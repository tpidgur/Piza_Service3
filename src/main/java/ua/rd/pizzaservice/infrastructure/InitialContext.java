package ua.rd.pizzaservice.infrastructure;

import ua.rd.pizzaservice.repository.InMemoryPizzaRepository;
import ua.rd.pizzaservice.repository.PizzaRepo;

public class InitialContext {
    private static Config config=new JavaConfig();


    public <T> T getInstance(String name) {
        Class<?> type = config.getImpl(name);
        try {
            return (T)type.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

}
