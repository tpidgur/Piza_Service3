package ua.rd.pizzaservice.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.PizzaService;

public class PizzaConverter implements Converter<String, Pizza> {
    @Autowired
    private PizzaService pizzaService;

    @Override
    public Pizza convert(String pizzaId) {
        System.out.println("pizzaConverter");
        if(pizzaId==null||pizzaId.isEmpty()){
            return new Pizza();
        }
        if (pizzaId != null) {
            return pizzaService.find(Long.valueOf(pizzaId));
        } else return null;
    }
}
