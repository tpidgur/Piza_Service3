package ua.rd.pizzaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;

import ua.rd.pizzaservice.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleOrderService implements OrderService {
    private final int MAX_PIZZAS_AMOUNT = 10;
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;
    private final SimpleDiscountService discountService;

    @Autowired
    public SimpleOrderService(OrderRepository orderRepository, PizzaService pizzaService,
                              SimpleDiscountService discountService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
        this.discountService = discountService;
    }


    public Order placeNewOrder(Customer customer, Long... pizzasID) {
        isPizzasAmountLessThanMaxAllowable(pizzasID.length);
        Order order = new Order(customer, convertIdsArrayInListOfPizzas(pizzasID));
        orderRepository.save(order);
        createNewCardIfNotExist(order);
        return order;
    }

    private void isPizzasAmountLessThanMaxAllowable(int pizzaNumber) {
        if (pizzaNumber > MAX_PIZZAS_AMOUNT) {
            throw new RuntimeException("The chosen amount of pizzas" +
                    " is higher than allowed");
        }
    }

    private List<Pizza> convertIdsArrayInListOfPizzas(Long[] pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();
        for (Long id : pizzasID) {
            pizzas.add(findPizaById(id));  // get Pizza from predifined in-memory list
        }
        return pizzas;
    }

    private Pizza findPizaById(Long id) {
        return pizzaService.find(id);
    }


    private void createNewCardIfNotExist(Order order) {
        if (order.getCustomer() != null) {
            order.getCustomer().createNewCardIfNotExist();
        }

    }


    public void changeStatus(Long orderId, Order.Status newStatus) {
        Order order = findOrderById(orderId);
        if (newStatus == Order.Status.CANCELLED) {
            order.setStatus(Order.Status.CANCELLED);
        } else if (newStatus == Order.Status.IN_PROGRESS &&
                order.getStatus() == Order.Status.NEW) {
            order.setStatus(Order.Status.IN_PROGRESS);
        } else if (newStatus == Order.Status.DONE &&
                order.getStatus() == Order.Status.IN_PROGRESS) {
            order.setStatus(Order.Status.DONE);
        } else throw new RuntimeException("The status " + newStatus + " isn't allowed");
    }

    public void closeOrder(Long orderId) {
        Order order = findOrderById(orderId);
        changeStatus(orderId, Order.Status.DONE);
        updateCummulativeCardBalance(order);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.getOrder(orderId);
    }

    private void updateCummulativeCardBalance(Order order) {
        BigDecimal oldBalance = order.getCustomer().getCard().getBalance();
        BigDecimal newBalance = calculateTotalPriceWithAllDiscounts(order);
        order.getCustomer().getCard().setBalance(oldBalance.add(newBalance));
    }

    private BigDecimal calculateTotalPriceWithAllDiscounts(Order order) {
        discountService.init();
        BigDecimal discounts = discountService.calculateTotalDiscount(order);
        BigDecimal totalPrice = order.calculateTotalPrice();
        return totalPrice.subtract(discounts);
    }


    //варто перевірити,що статус не закритий у завки
    public void addPizzasToExistingOrder(long orderId, Long... pizzasID) {
        Order order = findOrderById(orderId);
        isPizzasAmountLessThanMaxAllowable(order.getPizzas().size() + pizzasID.length);
        convertIdsArrayInListOfPizzas(pizzasID);
        order.addAdditionalPizzas(convertIdsArrayInListOfPizzas(pizzasID));
    }
}
