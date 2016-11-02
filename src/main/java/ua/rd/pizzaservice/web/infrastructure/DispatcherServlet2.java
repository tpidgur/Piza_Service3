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

public class DispatcherServlet2 extends HttpServlet {
    private ConfigurableApplicationContext webContext;
    ConfigurableApplicationContext[] applicationContexts;
    Map<String, Method> methodsMap = new HashMap<>();


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
        methodsMap = getMethodsWithAnnotation(HelloController.class);//всіх дітей від MyController.class, повертаэ мепу
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
        String url = req.getRequestURI();
        String methodName = getMethodName(url);
        System.out.println("methodName:" + methodName);
        String controllerName = "/hello";//метод який парсить попередній /  та останній /:отримує controllerName та methodName
        MyController controller = (MyController) webContext.getBean(controllerName, MyController.class);

        //HandlerMapping handlerMapping = new SimpleURLHandlerMapping();
       // MyController controller1 = handlerMapping.getController(req);
        if (controller != null) {
            req.setAttribute("methodObject", methodsMap.get(methodName));
            controller.handleRequest(req, resp);
        }
    }


    public Map<String, Method> getMethodsWithAnnotation(Class<?> type) {
        Map<String, Method> methodList = new HashMap<>();
        Method[] methods = type.getDeclaredMethods();
        System.out.println(Arrays.toString(methods));
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyRequestMapping.class)) {
                methodList.put(method.getAnnotation(MyRequestMapping.class).value(), method);
            }
        }
        return methodList;
    }

    private Class<?>[] getDeclaredClasses(Object o) {
        List<Class<?>> classes = new ArrayList<>();
        Class<?> klazz = o.getClass();
        while (klazz != null) {
            Collections.addAll(classes, klazz);
            klazz = klazz.getSuperclass();
        }
        return classes.stream().toArray(Class<?>[]::new);
    }

    private String getMethodName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
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
