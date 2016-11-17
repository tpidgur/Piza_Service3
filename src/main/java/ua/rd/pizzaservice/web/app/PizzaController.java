package ua.rd.pizzaservice.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.PizzaService;

import java.util.List;

@Controller
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @RequestMapping("/hello")
    // @ResponseBody
    public String hello() {
        return "hello";
    }

    @RequestMapping("/pizzas")
    public ModelAndView findAll(ModelAndView modelAndView) {
        modelAndView.setViewName("pizzas");
        modelAndView.addObject("pizzasList", pizzaService.findAll());
        return modelAndView;
    }

//    @RequestMapping(name="/create", method = RequestMethod.POST)
//    public String create() {
//        return "newpizza";
//    }
    @RequestMapping(name="/addnew", method = RequestMethod.POST)
    public String addNew(@ModelAttribute Pizza pizza) {
        System.out.println(pizza);
        pizzaService.save(pizza);
        return "redirect:pizzas";
    }


}
