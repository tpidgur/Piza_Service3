package ua.rd.pizzaservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.rd.pizzaservice.services.PizzaService;
import ua.rd.pizzaservice.web.infrastructure.MyController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller("/hello")
public class HelloController implements MyController {
    @Autowired
    private PizzaService pizzaService;
    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            out.println("Hello from HelloServlet");
           out.println(pizzaService.find(1L));
        }
    }
}
