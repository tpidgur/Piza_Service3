package ua.rd.pizzaservice.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.services.PizzaService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Secured("IS_AUTHENTICATED_FULLY")
@RequestMapping("/pizzas")
public class PizzaController {
    @Autowired
    private PizzaService pizzaService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/index")
    public String init() {
        return "index";
    }

    @RequestMapping
    @Secured("IS_AUTHENTICATED_FULLY")
    public ModelAndView findAll(ModelAndView modelAndView) {
        System.out.println("===findAll====");
        modelAndView.setViewName("pizzas");
        modelAndView.addObject("pizzasList", pizzaService.findAll());
        return modelAndView;
    }


    @RequestMapping(value = "/pizza/create", method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public String createPizza(Model model) {
        System.out.println("===createPizza====");
        Pizza pizza = new Pizza();
        model.addAttribute("pizza", pizza);
        model.addAttribute("types", Pizza.PizzaType.values());
        return "pizza";
    }

    @RequestMapping(value = "/{pizzaId}/edit", method = RequestMethod.POST)
    public String updatePizza(Model model, @PathVariable("pizzaId") Long pizzaId) {
        System.out.println("===updatePizza====");
        model.addAttribute("pizza", pizzaService.find(pizzaId));
        model.addAttribute("types", Pizza.PizzaType.values());
        return "pizza";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public String save(@ModelAttribute Pizza pizza, Model model) {
        System.out.println("===savePizza====" + pizza);
        pizzaService.save(pizza);
        List<Pizza> pizzas = pizzaService.findAll();
        model.addAttribute("pizzas", pizzas);
        return "redirect:/app/pizzas";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("pizzaId") Long pizzaId, @RequestParam("isDelete") boolean isDelete) {
        System.out.println(isDelete + "===deletePizza====" + pizzaId);
        if (isDelete) {
            pizzaService.delete(pizzaId);
        }
        return "redirect:/app/pizzas";
    }

    //    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
//    public String confirm(@RequestParam("id") Long pizzaId, Model model) {
//        model.addAttribute("id", pizzaId);
//        return "confirmation";
//    }
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirm(@RequestParam("id") Long pizzaId, Model model) {
        model.addAttribute("id", pizzaId);
        return "confirmation";
    }
}
