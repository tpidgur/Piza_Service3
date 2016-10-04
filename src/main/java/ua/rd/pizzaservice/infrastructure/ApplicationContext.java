package ua.rd.pizzaservice.infrastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext implements Context {

    private final Config config;
    private final Map<String, Object> beans = new HashMap<>();

    public ApplicationContext(Config config) {
        this.config = config;
    }


    @Override
    public <T> T getBean(String name) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (beans.containsKey(name)) {
            return (T) beans.get(name);
        }
        Class<?> type = config.getImpl(name);
        Constructor<?> constructor = type.getConstructors()[0];
        if (constructor.getParameterCount() == 0) {
            try {
                T bean = (T) type.newInstance();
                beans.put(name, bean);
                return bean;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] params = new Object[constructor.getParameterCount()];
            for (int i = 0; i < constructor.getParameterCount(); i++) {
                String beanName = parameterTypes[i].getSimpleName();
             String lowerCaseBeanName=  toLowerCase(beanName);
                params[i] = getBean(lowerCaseBeanName);
            }
            T bean = (T) constructor.newInstance(params);
            beans.put(name, bean);
            return bean;
        }

    }
   String toLowerCase(String beanName){
       char c[] = beanName.toCharArray();
       c[0] = Character.toLowerCase(c[0]);
     return new String(c);
   }




}
