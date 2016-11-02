package ua.rd.pizzaservice.web.infrastructure;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;

public class SimpleURLHandlerMapping implements HandlerMapping, ApplicationContextAware {
    private ApplicationContext webContext;

//    public SimpleURLHandlerMapping(ConfigurableApplicationContext webContext) {
//        this.webContext = webContext;
//    }

    public SimpleURLHandlerMapping() {
    }

    @Override
    public MyController getController(HttpServletRequest request) {
        String url = request.getRequestURI();
        String controllerName = getControllerName(url);
        return webContext.getBean(controllerName, MyController.class);
    }

    private String getControllerName(String url) {
        return url.substring(url.lastIndexOf("/"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.webContext = applicationContext;
    }
}
