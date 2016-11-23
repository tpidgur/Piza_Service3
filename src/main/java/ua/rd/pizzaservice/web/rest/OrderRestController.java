package ua.rd.pizzaservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.services.OrderService;

import java.math.BigDecimal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class OrderRestController {
    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "order/{orderID}", method = RequestMethod.GET)
    public ResponseEntity<Order> findOrderById(@PathVariable("orderID") Long orderId) {
        Order order = orderService.find(orderId);
        if (order == null) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        Link link = linkTo(methodOn(OrderRestController.class)
                .findOrderById(orderId)).withSelfRel();
        order.add(link);
        return new ResponseEntity<Order>(order, HttpStatus.FOUND);
    }

    @RequestMapping(value = "order/{orderID}/total", method = RequestMethod.GET)
    ResponseEntity<BigDecimal> getTotalWithoutDiscount(@PathVariable("orderID")Long orderId) {
        BigDecimal total=  orderService.getTotalWithoutDiscount(orderId);
        if (total == null) {
            return new ResponseEntity<BigDecimal>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<BigDecimal>(total, HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = "order/{orderID}/discount", method = RequestMethod.GET)
    ResponseEntity<BigDecimal> getTotalDiscount(@PathVariable("orderID")Long orderId) {
        BigDecimal total=  orderService.getTotalDiscountAmount(orderId);
        if (total == null) {
            return new ResponseEntity<BigDecimal>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<BigDecimal>(total, HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = "order/{orderID}/total/withdiscount", method = RequestMethod.GET)
    ResponseEntity<BigDecimal> getTotalWithDiscount(@PathVariable("orderID")Long orderId) {
        BigDecimal total=  orderService.getTotalWithDiscount(orderId);
        if (total == null) {
            return new ResponseEntity<BigDecimal>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<BigDecimal>(total, HttpStatus.FOUND);
        }
    }

//    @RequestMapping(value = "order", method = RequestMethod.POST)
//    public ResponseEntity<Void> placeNewOrder(@RequestBody Customer customer,
//                                              @RequestBody Long... orderId) {
//        orderService.placeNewOrder(customer, orderId);
//
//    }


}
