package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.OrderRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService implements OrderService {
    private final int MAX_PIZZAS_AMOUNT = 10;
    private final OrderRepo orderRepo;
    private final PizzaService pizzaService;


    public SimpleOrderService(OrderRepo orderRepo, PizzaService pizzaService) {
        this.orderRepo = orderRepo;
        this.pizzaService = pizzaService;
    }

    public Order placeNewOrder(Customer customer, Long... pizzasID) {
        isPizzasAmountLessThanMaxAllowable(pizzasID.length);
        Order order = new Order(customer, getPizzasListById(pizzasID));
        createNewCardIfNotExist(order);
        orderRepo.save(order);
        return order;
    }

    public void closeOrder(Long orderId) {
       Order order= findOrderById(orderId);
        order.setStatus(Order.Status.DONE);
        updateCummulativeCardBalance(order);
    }

    private void updateCummulativeCardBalance(Order order) {
        BigDecimal oldBalance = order.getCustomer().getCard().getBalance();
        BigDecimal newBalance = order.calculateTotalPriceWithDiscount();
        order.getCustomer().getCard().setBalance(oldBalance.add(newBalance));
    }

    private void createNewCardIfNotExist(Order order) {
        if (!order.getCustomer().hasCard()) {
            order.getCustomer().createNewCard();
        }
    }

    private void isPizzasAmountLessThanMaxAllowable(int pizzaNumber) {
        if (pizzaNumber > MAX_PIZZAS_AMOUNT) {
            throw new RuntimeException("The chosen amount of pizzas" +
                    " is higher than allowed");
        }
    }

    private List<Pizza> getPizzasListById(Long[] pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();
        for (Long id : pizzasID) {
            pizzas.add(findPizaById(id));  // get Pizza from predifined in-memory list
        }
        return pizzas;
    }


    Pizza findPizaById(Long id) {
        return pizzaService.find(id);
    }

    public Order findOrderById(Long orderId) {
        return orderRepo.getOrder(orderId);
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


    public BigDecimal getTotalOrderPrice(Long orderId) {
        return findOrderById(orderId).calculateTotalPrice();
    }

    public BigDecimal getTotalOrderPriceWithDiscount(Long orderId) {
        return findOrderById(orderId).calculateTotalPriceWithDiscount();
    }

    public void addPizzasToExistingOrder(long orderId, Long... pizzasID) {
        Order order = findOrderById(orderId);
        isPizzasAmountLessThanMaxAllowable(order.getPizzas().size() + pizzasID.length);
        getPizzasListById(pizzasID);
        order.addAdditionalPizzas(getPizzasListById(pizzasID));
    }
}
