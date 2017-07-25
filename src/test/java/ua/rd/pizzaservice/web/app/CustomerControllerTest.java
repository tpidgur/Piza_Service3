package ua.rd.pizzaservice.web.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.rd.pizzaservice.domain.Address;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.PizzaCard;
import ua.rd.pizzaservice.services.CustomerService;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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

    private static final long CUSTOMER_ID = 15l;
    private Customer customer = new Customer();
    private static final String EDIT_CUSTOMER_ON_ID_URL = HOME_CUSTOMER_CONTROLLER_URL + "/" + CUSTOMER_ID + "/edit";
    private static final String CUSTOMER_VIEW_NAME = "customer";
    private static final String CUSTOMER_ATTRIBUTE = "customer";
    private static final String CREATE_CUSTOMER_URL = HOME_CUSTOMER_CONTROLLER_URL + "/customer/create";
    private static final Customer EXPECTED_CUSTOMER_FROM_REGISTRATION_FORM
            = new Customer("Jack", new Address("c.Kiev"), new PizzaCard(new BigDecimal(100)));
    private static final String SAVE_CUSTOMER_URL = "/customers/save";
    private static final String CUSTOMER_NAME = "Jack";
    private static final String CUSTOMER_ADRESS = "c.Kiev";
    private static final String CUSTOMER_BALANCE = "100";
    private static final String NAME_PARAMETER = "name";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String PIZZACARD_BALANCE_PARAMETER = "balance";
    private static final String SAVE_CUSTOMER_REDIRECT_URL = "/app" + HOME_CUSTOMER_CONTROLLER_URL;

    @Before
    public void setup() {
        controller = new CustomerController(customerService);
        mockMvc = standaloneSetup(controller)
                .build();
        when(customerService.findAll()).thenReturn(CUSTOMERS_LIST_VALUE);
        when(customerService.find(CUSTOMER_ID)).thenReturn(customer);
    }

    @Test
    public void shouldFindAllCustomers() throws Exception {
        mockMvc = standaloneSetup(controller)
                .setSingleView(
                        new InternalResourceView(PREFIX + CUSTOMERS_VIEW_NAME))
                .build();
        mockMvc.perform(get(HOME_CUSTOMER_CONTROLLER_URL))
                .andExpect(view().name(CUSTOMERS_VIEW_NAME))
                .andExpect(model().attributeExists(CUSTOMERS_LIST_ATTRIBUTE))
                .andExpect(model().attribute(CUSTOMERS_LIST_ATTRIBUTE, CUSTOMERS_LIST_VALUE));
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {
        mockMvc.perform(post(EDIT_CUSTOMER_ON_ID_URL))
                .andExpect(view().name(CUSTOMER_VIEW_NAME))
                .andExpect(model().attributeExists(CUSTOMER_ATTRIBUTE))
                .andExpect(model().attribute(CUSTOMER_ATTRIBUTE, customer));
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        mockMvc.perform(get(CREATE_CUSTOMER_URL))
                .andExpect(view().name(CUSTOMER_VIEW_NAME))
                .andExpect(model().attributeExists(CUSTOMER_ATTRIBUTE))
                .andExpect(model().attribute(CUSTOMER_ATTRIBUTE, customer));
    }

    @Test
    public void shouldSaveCustomer() throws Exception {
        when(customerService.save(EXPECTED_CUSTOMER_FROM_REGISTRATION_FORM)).thenReturn(EXPECTED_CUSTOMER_FROM_REGISTRATION_FORM);
        mockMvc.perform(post(SAVE_CUSTOMER_URL)
                .param(NAME_PARAMETER, CUSTOMER_NAME)
                .param(ADDRESS_PARAMETER, CUSTOMER_ADRESS)
                .param(PIZZACARD_BALANCE_PARAMETER, CUSTOMER_BALANCE))
                .andExpect(redirectedUrl(SAVE_CUSTOMER_REDIRECT_URL));
        verify(customerService, atLeastOnce()).save(EXPECTED_CUSTOMER_FROM_REGISTRATION_FORM);
    }
}