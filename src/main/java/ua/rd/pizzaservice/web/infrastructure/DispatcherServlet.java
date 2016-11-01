package ua.rd.pizzaservice.web.infrastructure;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
    private ConfigurableApplicationContext webContext;
    ConfigurableApplicationContext[] applicationContexts;

    @Override
    public void init() throws ServletException {
        String contextLocations = getServletContext().getInitParameter("contextConfigLocation");
        String[] contexts = contextLocations.split(" ");
        applicationContexts = new ConfigurableApplicationContext[contexts.length];


        for (int i = 0; i < applicationContexts.length; i++) {
           // ConfigurableApplicationContext context = null;
            if (i == 0) {
                applicationContexts[i] = new ClassPathXmlApplicationContext(contexts[i]);
            } else {
                applicationContexts[i] = new ClassPathXmlApplicationContext(new String[]{contexts[i]}, applicationContexts[i - 1]);
            }
           // applicationContexts[i] = context;
        }
        String webContextConfigLocation = getInitParameter("contextConfigLocation");
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
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String url = req.getRequestURI();
        String controllerName = getControllerName(url);

        MyController controller = (MyController) webContext.getBean(controllerName, MyController.class);

        // MyController controller = getController(controllerName);
        if (controller != null) {
            controller.handleRequest(req, resp);
        }

//        try (PrintWriter out = resp.getWriter()) {
//            out.println(controllerName);
//            out.print(url);
//        }
    }

    private String getControllerName(String url) {
        return url.substring(url.lastIndexOf("/"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
        //super.doPost(req, resp);
    }
}
