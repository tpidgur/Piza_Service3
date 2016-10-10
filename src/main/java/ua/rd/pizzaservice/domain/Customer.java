package ua.rd.pizzaservice.domain;

public class Customer {
    private Long id;
    private String name;
    private static long counter;
    private String address;


    public Customer(String name) {
        this.name = name;
        id = counter++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getCounter() {
        return counter;
    }

    public static void setCounter(long counter) {
        Customer.counter = counter;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
