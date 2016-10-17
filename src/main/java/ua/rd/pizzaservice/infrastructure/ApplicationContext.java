package ua.rd.pizzaservice.infrastructure;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext implements Context {

    private final Config config;
    private final Map<String, Object> beans = new HashMap<>();

    public ApplicationContext(Config config) {
        this.config = config;
    }




    @Override
    public <T> T getBean(String beanName) {
        Class<?> type = config.getImpl(beanName);
        Object bean = beans.get(beanName);
        if (bean != null) {
            return (T) bean;
        }
        BeanBuilder builder = new BeanBuilder(type);
        builder.createBean();
        //  builder.callPostCreateMethod();
        try {
            builder.callInitMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.createBeanProxy();
        bean = builder.build();
        beans.put(beanName, bean);
        return (T) bean;
    }


    class BeanBuilder<T> {
        private final Class<?> type;
        private T bean;


        BeanBuilder(Class<?> type) {
            this.type = type;
        }


        public void createBean() {
            try {
                Constructor<?> constructor = getTypeConstructor(type);
                if (constructor.getParameterCount() == 0) {
                    bean = (T) type.newInstance();
                } else {
                    bean = (T) constructor.newInstance(getParameters(constructor));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private Constructor<?> getTypeConstructor(Class<?> clazz) {

            return clazz.getConstructors()[0];
        }

        private Object[] getParameters(Constructor<?> constructor) {
            Parameter[] constructorParams = constructor.getParameters();
            Object[] beans = new Object[constructorParams.length];
            for (int i = 0; i < constructorParams.length; i++) {
                beans[i] = getBean(convertToBeanName(constructorParams[i].getType()));
            }
            return beans;
        }

        private String convertToBeanName(Class<?> typeName) {
            char[] chars = typeName.getSimpleName().toCharArray();
            chars[0] += 32; //converts to lowerCase first char;
            return new String(chars);
        }

        public T build() {
            return bean;

        }

        public void callInitMethod() throws Exception {
            Class<?> clazz = bean.getClass();
            Method method;
            try {
                method = clazz.getMethod("init");
            } catch (NoSuchMethodException e) {
                return;
            }
            method.invoke(bean);
        }


        public void createBeanProxy() {
            if (hasMethodAnnotation(BenchMark.class)) {
                Class<?> clazz = bean.getClass();
                T original = bean;
                bean = (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), type.getInterfaces(),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                Method beanMethod = type.getMethod(method.getName(), method.getParameterTypes());
                                if (isBenchMarkAnnotationPresentAndTrue(beanMethod)) {
                                    return wrapMethodInBenchmark(original, beanMethod, args);
                                }
                                return beanMethod.invoke(original, args);
                            }
                        });
            }


        }

        public boolean hasMethodAnnotation(Class<? extends Annotation> aClass) {
            return Arrays.stream(type.getDeclaredMethods()).anyMatch(e -> e.isAnnotationPresent(aClass));
        }

        public <T> Object wrapMethodInBenchmark(T original, Method beanMethod, Object[] args)
                throws InvocationTargetException, IllegalAccessException {
            long start = System.nanoTime();
            Object invoke = beanMethod.invoke(original, args);
            long end = System.nanoTime();
            System.out.println(String.format("Method '%s', execution time %d", beanMethod.getName(), end - start));
            return invoke;
        }

        public boolean isBenchMarkAnnotationPresentAndTrue(Method beanMethod) {
            return beanMethod.isAnnotationPresent(BenchMark.class) && beanMethod.getAnnotation(BenchMark.class).value();
        }


    }


}
