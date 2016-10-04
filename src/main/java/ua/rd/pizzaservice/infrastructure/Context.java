package ua.rd.pizzaservice.infrastructure;

import java.lang.reflect.InvocationTargetException;

public interface Context {
   <T>T getBean(String beanName) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
