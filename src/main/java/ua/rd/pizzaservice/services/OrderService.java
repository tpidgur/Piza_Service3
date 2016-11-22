package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;

import java.math.BigDecimal;
import java.util.List;


public interface OrderService {
    Order placeNewOrder(Customer customer, Long... pizzasID);

    Order findOrderById(Long orderId);

    void addPizzasToExistingOrder(Long orderId, Long... pizzaId);

    void removePizzaFromExistingOrder(Long orderId, Long pizzaId);

    void setCancelStatus(Long orderId);

    void setDoneStatus(Long orderId);

    void setInProgressStatus(Long orderId);

    BigDecimal getTotalDiscountAmount(Long orderId);

    BigDecimal getTotalWithoutDiscount(Long orderId);

    BigDecimal getTotalWithDiscount(Long orderId);

    List<Order> findAll();


}
