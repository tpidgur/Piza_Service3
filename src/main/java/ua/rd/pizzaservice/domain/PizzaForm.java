package ua.rd.pizzaservice.domain;

import java.util.HashMap;
import java.util.Map;

public class PizzaForm {
    private Map<String,String> pizzaMap=new HashMap<String,String>();

    public Map<String, String> getPizzaMap() {
        return pizzaMap;
    }

    public void setPizzaMap(Map<String, String> pizzaMap) {
        this.pizzaMap = pizzaMap;
    }

    @Override
    public String toString() {
        return "PizzaForm{" +
                "pizzaMap=" + pizzaMap +
                '}';
    }
}
