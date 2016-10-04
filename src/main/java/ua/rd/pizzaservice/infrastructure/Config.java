package ua.rd.pizzaservice.infrastructure;

public interface Config {

    public Class<?> getImpl(String name);
}
