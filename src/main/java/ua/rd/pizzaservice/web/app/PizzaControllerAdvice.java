package ua.rd.pizzaservice.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.PizzaService;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class PizzaControllerAdvice {
    @Autowired
    private PizzaService pizzaService;

    @ModelAttribute
    public Pizza status(@RequestParam(name = "pizzaId", required = false) Pizza pizza) {
        System.out.println("Advice" + pizza);
        return pizza;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String error(Exception e, HttpServletRequest req, Model model) {
        model.addAttribute("ex", e);
        model.addAttribute("url", req.getRequestURL());
        return "error";
    }
}
