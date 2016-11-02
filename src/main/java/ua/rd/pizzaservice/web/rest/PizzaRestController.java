package ua.rd.pizzaservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.PizzaService;

import java.math.BigDecimal;


@RestController
public class PizzaRestController {
    @Autowired
    private PizzaService pizzaService;

    @RequestMapping(value = "pizza/{pizzaID}", method = RequestMethod.GET)
    public Pizza pizza(@PathVariable("pizzaID") Long pizzaID) {
        return pizzaService.find(1L);
                //new Pizza("New York Style Pizza", BigDecimal.ONE, Pizza.PizzaType.MEAT);
    }
    @RequestMapping(value = "pizza", method = RequestMethod.POST )
    public void pizza(@RequestBody Pizza pizza) {
        System.out.println(pizza);

    }
}
