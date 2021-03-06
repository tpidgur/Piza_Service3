package ua.rd.pizzaservice.web.infrastructure;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizzaservice.infrastructure.MyRequestMapping;
import ua.rd.pizzaservice.web.HelloController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class DispatcherServlet extends HttpServlet {
    private ConfigurableApplicationContext webContext;
    ConfigurableApplicationContext[] applicationContexts;


    @Override
    public void init() throws ServletException {
        String contextLocations = getServletContext().getInitParameter("contextConfigLocation");
        String[] contexts = contextLocations.split(" ");
        applicationContexts = new ConfigurableApplicationContext[contexts.length];
        for (int i = 0; i < applicationContexts.length; i++) {
            if (i == 0) {
                applicationContexts[i] = new ClassPathXmlApplicationContext(contexts[i]);
            } else {
                applicationContexts[i] = new ClassPathXmlApplicationContext(new String[]{contexts[i]},
                        applicationContexts[i - 1]);
            }
        }
        String webContextConfigLocation = getInitParameter("servletContextConfigLocation");
        webContext = new ClassPathXmlApplicationContext(new String[]{webContextConfigLocation},
                applicationContexts[applicationContexts.length - 1]);
        }

    @Override
    public void destroy() {
        webContext.close();
        for (int i = applicationContexts.length - 1; i >= 0; i--) {
            applicationContexts[i].close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        HandlerMapping handlerMapping = webContext
                .getBean("handlerMappingStrategy", HandlerMapping.class);
        //new SimpleURLHandlerMapping(webContext);
        MyController controller = handlerMapping.getController(req);
        if (controller != null) {
            controller.handleRequest(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
