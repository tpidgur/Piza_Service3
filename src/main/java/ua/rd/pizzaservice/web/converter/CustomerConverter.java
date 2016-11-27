package ua.rd.pizzaservice.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.services.CustomerService;

public class CustomerConverter implements Converter<String, Customer> {
    @Autowired
    private CustomerService customerService;

    @Override
    public Customer convert(String customerId) {
        System.out.println("customerConverter"+ customerId);
        if(customerId ==null|| customerId.isEmpty()){
            return new Customer();
        }
        if (customerId != null) {
            return customerService.find(Long.valueOf(customerId));
        } else return null;
    }
}
