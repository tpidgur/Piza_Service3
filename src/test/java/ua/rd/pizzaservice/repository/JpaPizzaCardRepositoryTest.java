package ua.rd.pizzaservice.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.PizzaCard;

import static org.junit.Assert.*;

public class JpaPizzaCardRepositoryTest extends RepositoryTestConfig {
    @Autowired
    private PizzaCardRepository pizzaCardRepository;

    @Test
    public void findAllTest() {

    }

    @Test
    public void findAllByCustomerTest() {
//not realized yet
    }

    @Test
    public void findTest() {
        PizzaCard actual = initializeOnePizzaCard();
        PizzaCard expected = pizzaCardRepository.find(actual.getId());
        assertEquals(expected, actual);
    }

    private PizzaCard initializeOnePizzaCard() {
        PizzaCard actual = new PizzaCard();
        actual=pizzaCardRepository.save(actual);
        return actual;
    }

    @Test
    public void saveTest() {
        PizzaCard pizzaCard = initializeOnePizzaCard();
        assertNotNull(pizzaCard.getId());
    }

}