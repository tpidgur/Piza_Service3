package ua.rd.pizzaservice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.domain.PizzaCard;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JpaPizzaCardRepositoryITest extends RepositoryTestConfig {
    @Autowired
    private PizzaCardRepository pizzaCardRepository;

    @Before
    public void intialTune() {
        jdbcTemplate.update("DELETE FROM pizzacards");
    }

    @Test
    public void findTest() {
        PizzaCard actual = initializeOnePizzaCard();
        PizzaCard expected = pizzaCardRepository.find(actual.getId());
        assertEquals(expected, actual);
    }


    @Test
    public void findAllTest() {
        List<PizzaCard> expected = initializeTwoCards();
        List<PizzaCard> actual = pizzaCardRepository.findAll();
        assertEquals(actual, expected);
    }


    @Test
    public void saveTest() {
        PizzaCard pizzaCard = initializeOnePizzaCard();
        assertNotNull(pizzaCard.getId());
    }

    private PizzaCard initializeOnePizzaCard() {
        return pizzaCardRepository.save(new PizzaCard());
    }

    private List<PizzaCard> initializeTwoCards() {
        PizzaCard card1 = pizzaCardRepository.save(new PizzaCard());
        PizzaCard card2 = pizzaCardRepository.save(new PizzaCard());
        return Arrays.asList(card1, card2);
    }


}