package ua.rd.pizzaservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OrderHolder {
        private String orderId;
    private String customerId;
    private String address;
    private String status;
        private String date;
    private Map<String, String> pizzas = new HashMap<>();

    @Override
    public String toString() {
        return "OrderHolder{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", pizzas=" + pizzas +
                '}';
    }
}
