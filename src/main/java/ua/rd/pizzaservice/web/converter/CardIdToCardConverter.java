package ua.rd.pizzaservice.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ua.rd.pizzaservice.domain.PizzaCard;
import ua.rd.pizzaservice.services.PizzaCardService;

public class CardIdToCardConverter implements Converter<String, PizzaCard> {
    @Autowired
    private PizzaCardService service;

    @Override
    public PizzaCard convert(String id) {
        try {
            System.out.println("PizzaCard Converter!!!!!!!!");
            return service.find(Long.valueOf(id));
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
