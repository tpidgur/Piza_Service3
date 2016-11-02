package ua.rd.pizzaservice.web.infrastructure;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    MyController getController(HttpServletRequest req) ;
}
