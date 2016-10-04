package ua.rd.pizzaservice.domain;

public class Customer {
    private Long id;
    private String name;
    private static long counter;

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

    public Customer( String name) {

        this.name = name;
        id = counter++;
    }
}
