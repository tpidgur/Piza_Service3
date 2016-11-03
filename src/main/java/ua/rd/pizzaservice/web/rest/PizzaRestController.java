package ua.rd.pizzaservice.web.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.PizzaService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
public class PizzaRestController {
    @Autowired
    private PizzaService pizzaService;

//    @RequestMapping(value = "pizza/{pizzaID}", method = RequestMethod.GET)
//    public Pizza pizza(@PathVariable("pizzaID") Long pizzaID) {
//        return pizzaService.find(1L);
        //new Pizza("New York Style Pizza", BigDecimal.ONE, Pizza.PizzaType.MEAT);
   // }

//    @RequestMapping(value = "pizza", method = RequestMethod.POST)
//    public void pizza(@RequestBody Pizza pizza) {
//        System.out.println(pizza);
//
//    }

//    @RequestMapping(value = "pizza/{pizzaID}", method = RequestMethod.GET)
//    public ResponseEntity<Pizza> pizza(@PathVariable("pizzaID") Long pizzaID) {
//        Pizza pizza = pizzaService.find(pizzaID);
//        if (pizza == null) {
//            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
//
//        }
//
//        return new ResponseEntity<Pizza>(pizza, HttpStatus.FOUND);
//
//    }



//
//
    @RequestMapping(value = "pizza/{pizzaID}", method = RequestMethod.GET)
    public ResponseEntity<Pizza> find(@PathVariable("pizzaID") Long pizzaID) {
        Pizza pizza = pizzaService.find(pizzaID);
        if (pizza == null) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        Link link=linkTo(methodOn(PizzaRestController.class).find(pizzaID)).withSelfRel();
        pizza.add(link);
        return new ResponseEntity<Pizza>(pizza, HttpStatus.FOUND);

    }

    @RequestMapping(value = "/pizza", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> create(@RequestBody Pizza pizza, UriComponentsBuilder builder) {
        System.out.println(pizza);
        Pizza p = pizzaService.save(pizza);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/pizza/{pizzaID}")
                        .buildAndExpand(p.getPizzaId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
