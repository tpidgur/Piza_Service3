package ua.rd.pizzaservice.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.services.OrderService;

public class OrderConverter implements Converter<String, Order> {
    @Autowired
    private OrderService orderService;

    @Override
    public Order convert(String orderId) {
        System.out.println("orderConverter"+ orderId);
        if(orderId ==null|| orderId.isEmpty()){
            return new Order();
        }
        if (orderId != null) {
            System.out.println(orderService.find(Long.valueOf(orderId)));
            return orderService.find(Long.valueOf(orderId));
        } else return null;
    }
}
