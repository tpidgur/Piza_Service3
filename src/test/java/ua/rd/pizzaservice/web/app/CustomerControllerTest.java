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

    private static final List<Customer> CUSTOMERS_LIST_VALUE = new LinkedList<>();
    private static final String PREFIX = "WEB-INF/jsp/";
    private static final String CUSTOMERS_VIEW_NAME = "customers";
    private static final String HOME_CUSTOMER_CONTROLLER_URL = "/customers";
    private static final String CUSTOMERS_LIST_ATTRIBUTE = "customerList";


    @Before
    public void setup() {
        controller = new CustomerController(customerService);
        mockMvc = standaloneSetup(controller)
                .setSingleView(
                        new InternalResourceView(PREFIX + CUSTOMERS_VIEW_NAME))
                .build();
        when(customerService.findAll()).thenReturn(CUSTOMERS_LIST_VALUE);
    }

    @Test
    public void shouldFindAllCustomers() throws Exception {
        mockMvc.perform(get(HOME_CUSTOMER_CONTROLLER_URL))
                .andExpect(view().name(CUSTOMERS_VIEW_NAME))
                .andExpect(model().attributeExists(CUSTOMERS_LIST_ATTRIBUTE))
                .andExpect(model().attribute(CUSTOMERS_LIST_ATTRIBUTE, CUSTOMERS_LIST_VALUE));
    }
}