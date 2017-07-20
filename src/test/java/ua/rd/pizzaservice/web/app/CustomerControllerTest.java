package ua.rd.pizzaservice.web.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.services.CustomerService;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    private CustomerController controller;
    @Mock
    private CustomerService customerService;
    private MockMvc mockMvc;
    private List<Customer> customers = new LinkedList<>();


    @Before
    public void setup() {
        controller = new CustomerController(customerService);
        mockMvc = standaloneSetup(controller)
                .setSingleView(
                        new InternalResourceView("WEB-INF/jsp/customers.jsp"))
                .build();
        when(customerService.findAll()).thenReturn(customers);
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(view().name("customers"))
                .andExpect(model().attributeExists("customerList"))
                .andExpect(model().attribute("customerList", customers));
    }
}