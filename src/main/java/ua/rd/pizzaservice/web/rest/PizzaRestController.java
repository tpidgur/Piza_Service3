package ua.rd.pizzaservice.web.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.PizzaService;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Secured("ROLE_ADMIN")
@RestController
public class PizzaRestController {
    @Autowired
    private PizzaService pizzaService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String[] hello() {
        return new String[]{"Hello from REST Controller!"};
    }




    @RequestMapping(value = "pizza/{pizzaID}", method = RequestMethod.GET)
    public ResponseEntity<Pizza> findPizzaById(@PathVariable("pizzaID") Long pizzaID) {
        Pizza pizza = pizzaService.find(pizzaID);
        if (pizza == null) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        Link link = linkTo(methodOn(PizzaRestController.class).findPizzaById(pizzaID)).withSelfRel();
        pizza.add(link);
        return new ResponseEntity<Pizza>(pizza, HttpStatus.FOUND);
    }

    @RequestMapping(value = "pizzas/", method = RequestMethod.GET)
    public ResponseEntity<List<Pizza>> findAll() {
        List<Pizza> pizzas = pizzaService.findAll();
        if (pizzas == null) {
            return new ResponseEntity<List<Pizza>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Pizza>>(pizzas, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/pizzas", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> addPizza(@RequestBody Pizza pizza, UriComponentsBuilder builder) {
        Pizza p = pizzaService.save(pizza);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/pizza/{pizzaID}")
                        .buildAndExpand(p.getPizzaId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
