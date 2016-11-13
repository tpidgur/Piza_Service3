package ua.rd.pizzaservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.services.OrderService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class OrderRestController {
    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "order/{orderID}", method = RequestMethod.GET)
    public ResponseEntity<Order> findOrderById(@PathVariable("orderID") Long orderID) {
        Order order = orderService.findOrderById(orderID);
        if (order == null) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        Link link = linkTo(methodOn(OrderRestController.class)
                .findOrderById(orderID)).withSelfRel();
        order.add(link);
        return new ResponseEntity<Order>(order, HttpStatus.FOUND);
    }
}
