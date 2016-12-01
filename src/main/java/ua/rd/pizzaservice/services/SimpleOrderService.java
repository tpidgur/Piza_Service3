package ua.rd.pizzaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.*;

import ua.rd.pizzaservice.infrastructure.BenchMark;
import ua.rd.pizzaservice.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


public class SimpleOrderService implements OrderService /*, ApplicationContextAware*/ {
    private final int MAX_PIZZAS_AMOUNT = 10;
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;
    private final DiscountService discountService;
    private final CustomerService customerService;
    private final AddressService addressService;
    // private ApplicationContext context;


    @Autowired
    public SimpleOrderService(OrderRepository orderRepository, PizzaService pizzaService,
                              DiscountService discountService, CustomerService customerService, AddressService addressService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
        this.discountService = discountService;
        this.customerService = customerService;
        this.addressService = addressService;
    }

//    @Override
//    public void setApplicationContext(ApplicationContext context) throws BeansException {
//        this.context = context;
//    }

    @BenchMark
    public Order placeNewOrder(Customer customer, Long... pizzasID) {
        isPizzasAmountLessThanMaxAllowable(pizzasID.length);
        Order order = createNewOrder();
        order.setCustomer(customer);
        order.setPizzas(convertIdMapInPizzaMap(convertIdListInIdMap(Arrays.asList(pizzasID))));
        createNewCardIfNotExist(order);
        Order newOrder = orderRepository.save(order);
        return newOrder;
    }

    // @BenchMark
    private void isPizzasAmountLessThanMaxAllowable(int pizzaNumber) {
        if (pizzaNumber > MAX_PIZZAS_AMOUNT) {
            throw new RuntimeException("The chosen amount of pizzas" +
                    " is higher than allowed");
        }
    }

    public Order createNewOrder() {
        throw new IllegalStateException();
    }

    protected Map<Long, Integer> convertIdListInIdMap(List pizzasID) {
        Map<Long, Integer> pizzaIdQuantity = new HashMap<>();
        Set<Long> uniqueSet = new HashSet<>(pizzasID);
        for (Long item : uniqueSet) {
            if (!pizzaIdQuantity.containsKey(item)) {
                pizzaIdQuantity.put(item, Collections.frequency(pizzasID, item));
            }
        }
        return pizzaIdQuantity;
    }

    protected Map<Pizza, Integer> convertIdMapInPizzaMap(Map<Long, Integer> pizzaIds) {
        Map<Pizza, Integer> pizzas = new HashMap<>();
        pizzaIds.forEach((s, integer) -> pizzas.put(findPizaById(s), integer));
        return pizzas;
    }

    private Pizza findPizaById(Long id) {
        return pizzaService.find(id);
    }

    // @BenchMark
    private void createNewCardIfNotExist(Order order) {
        if (order.getCustomer() != null) {
            order.getCustomer().createNewCardIfNotExist();
        }
    }


    protected void changeStatus(Long orderId, Order.Status newStatus) {
        Order order = find(orderId);
        if (newStatus == Order.Status.CANCELLED) {
            order.setStatus(Order.Status.CANCELLED);
        } else if (newStatus == Order.Status.IN_PROGRESS &&
                order.getStatus() == Order.Status.NEW) {
            order.setStatus(Order.Status.IN_PROGRESS);
        } else if (newStatus == Order.Status.DONE &&
                order.getStatus() == Order.Status.IN_PROGRESS) {
            order.setStatus(Order.Status.DONE);
        } else throw new RuntimeException("The status " + newStatus + " isn't allowed");
        System.out.println("!!!Order" + order);
        orderRepository.save(order);
    }


    public Order find(Long orderId) {
        System.out.println("orderRepository.find(orderId)" + orderRepository.find(orderId));

        return orderRepository.find(orderId);
    }

    @Override
    public void addPizzasToExistingOrder(Long orderId, Long... pizzaId) {
        Order order = find(orderId);
        checkStatus(order);
        isPizzasAmountLessThanMaxAllowable(order.getAmountOfPizzas() + pizzaId.length);
        Map<Pizza, Integer> additional = convertIdMapInPizzaMap(convertIdListInIdMap(Arrays.asList(pizzaId)));
        order.addPizzas(additional);
        orderRepository.save(order);
    }

    @Override
    public void removePizzaFromExistingOrder(Long orderId, Long pizzaId) {
        Order order = find(orderId);
        checkStatus(order);
        Pizza pizza = findPizaById(pizzaId);
        isOrderContainsPizza(order, pizza);
        order.removePizza(pizza);
        orderRepository.save(order);
    }

    @Override
    public void setCancelStatus(Long orderId) {
        changeStatus(orderId, Order.Status.CANCELLED);
    }

    @Override
    public void setDoneStatus(Long orderId) {
        updatePizzaCardBalance(orderId);
        changeStatus(orderId, Order.Status.DONE);
    }

    private void updatePizzaCardBalance(Long orderId) {
        Order order = find(orderId);
        System.out.println(order);
        PizzaCard card = order.getCustomer().getCard();
        card.setBalance(card.getBalance().add(getTotalWithDiscount(orderId)));
        orderRepository.save(order);
    }

    @Override
    public void setInProgressStatus(Long orderId) {
        changeStatus(orderId, Order.Status.IN_PROGRESS);
    }

    @Override
    public BigDecimal getTotalDiscountAmount(Long orderId) {
        return discountService.calculateTotalDiscount(find(orderId));
    }

    @Override
    public BigDecimal getTotalWithoutDiscount(Long orderId) {
        return find(orderId).calculateTotalPrice();
    }

    @Override
    public BigDecimal getTotalWithDiscount(Long orderId) {
        return getTotalWithoutDiscount(orderId).subtract(getTotalDiscountAmount(orderId));
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order update(Order order) {
        isPizzasAmountLessThanMaxAllowable(order.getAmountOfPizzas());
        return orderRepository.save(order);
    }

    @Override
    public Order convertOrderHolderToOrder(OrderHolder holder) {
        Order order = null;
        if (holder.getOrderId() != null) {
            order = find(Long.valueOf(holder.getOrderId()));
        } else {
            order = new Order();
        }
        return setOrderFields(holder, order);
    }

    private Order setOrderFields(OrderHolder holder, Order order) {
        order.setAddress(getAddress(holder));
        order.setCustomer(customerService.find(Long.valueOf(holder.getCustomerId())));
        createNewCardIfNotExist(order);
        order.setDate(LocalDate.parse(holder.getDate()));
        order.setPizzas(convertToPizzaMap(holder.getPizzas()));
        return orderRepository.save(order);
    }


    private Address getAddress(OrderHolder holder) {
        Address address = addressService.findByAddress(holder.getAddress());
        if (address == null) {
            address = addressService.save(new Address(holder.getAddress()));
        }
        return address;
    }

    protected Map<Pizza, Integer> convertToPizzaMap(Map<String, String> pizzaIds) {
        Map<Pizza, Integer> pizzas = new HashMap<>();
        pizzaIds.forEach((s, amount) -> pizzas.put(pizzaService.find(Long.valueOf(s)),
                Integer.valueOf(amount)));
        return pizzas;
    }

    private Customer findCustomer(Long orderId) {
        return customerService.find(orderId);
    }


    private void checkStatus(Order order) {
        Order.Status status = order.getStatus();
        if (status == Order.Status.CANCELLED || status == Order.Status.DONE) {
            throw new RuntimeException("Error modifying order that has been cancelled or done!");
        }
    }

    private void isOrderContainsPizza(Order order, Pizza pizza) {
        order.getPizzas().size();
        if (!order.getPizzas().containsKey(pizza) || order.getPizzas().get(pizza) == 0) {
            throw new RuntimeException("No pizzas have been found in the order!");
        }
    }


}
