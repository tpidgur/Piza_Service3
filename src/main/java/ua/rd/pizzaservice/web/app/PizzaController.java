package ua.rd.pizzaservice.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        System.out.println("===findAll====");
        modelAndView.setViewName("pizzas");
        modelAndView.addObject("pizzasList", pizzaService.findAll());
        return modelAndView;
    }


    @RequestMapping(name = "/create", method = RequestMethod.GET)
    public String createPizza(Model model) {
        System.out.println("===createPizza====");
        Pizza pizza = new Pizza();
        model.addAttribute("pizza", pizza);
        model.addAttribute("types", Pizza.PizzaType.values());
        return "pizza";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updatePizza(Model model) {
        System.out.println("===updatePizza====");
        model.addAttribute("types", Pizza.PizzaType.values());
        return "pizza";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save (@ModelAttribute Pizza pizza,Model model){
        System.out.println("===savePizza===="+pizza);
        pizzaService.save(pizza);
        List<Pizza>pizzas=pizzaService.findAll();
        model.addAttribute("pizzas",pizzas);
        return "redirect:pizzas";
    }
//    @ModelAttribute
//    public Pizza getPizzaById(@RequestParam(value = "pizzaId", required = false) Long pizzaId) {
//        if (pizzaId == null) {
//            System.out.println("====pizzaId======"+pizzaId);
//            return null;
//        }
//        return pizzaService.find(pizzaId);
//    }
}
