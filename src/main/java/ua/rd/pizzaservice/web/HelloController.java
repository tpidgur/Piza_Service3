package ua.rd.pizzaservice.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.infrastructure.MyRequestMapping;
import ua.rd.pizzaservice.repository.PizzaRepository;
import ua.rd.pizzaservice.services.PizzaService;
import ua.rd.pizzaservice.web.infrastructure.MyController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Controller("/hello")
public class HelloController implements MyController {
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        try (PrintWriter out = resp.getWriter()) {
            out.println("Hello from HelloServlet");
            Method method = (Method) req.getAttribute("methodObject");
            out.println(method.invoke(this));//винести в окремий метод інтерфейса MyController.зробити вкладений меп та передавати назву класа
        }
    }

    @MyRequestMapping("findAll")
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

}
