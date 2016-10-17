package ua.rd.pizzaservice.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class BenchMarkBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("Created " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
       // System.out.println("Initialized " + beanName);
               return createBeanProxy(bean);
    }

    public Object createBeanProxy(Object bean) {
        if (hasMethodAnnotation(BenchMark.class, bean)) {
            Object original = bean;
            Class<?> type = bean.getClass();
            bean = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), type.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Method beanMethod = type.getMethod(method.getName(),
                                    method.getParameterTypes());
                            if (isBenchMarkAnnotationPresentAndTrue(beanMethod)) {
                                System.out.println("BenchmarkAnnot "+type);
                                return wrapMethodInBenchmark(original, beanMethod, args);
                            }
                            return beanMethod.invoke(original, args);
                        }
                    });
        }
        return bean;
    }

    public boolean hasMethodAnnotation(Class<? extends Annotation> annotationClass, Object bean) {
        return Arrays.stream(bean.getClass().getDeclaredMethods())
                .anyMatch(e -> e.isAnnotationPresent(annotationClass));
    }

    public boolean isBenchMarkAnnotationPresentAndTrue(Method beanMethod) {
        return beanMethod.isAnnotationPresent(BenchMark.class) && beanMethod.getAnnotation(BenchMark.class).value();
    }


    public <T> Object wrapMethodInBenchmark(T original, Method beanMethod, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
        long start = System.nanoTime();
        Object invoke = beanMethod.invoke(original, args);
        long end = System.nanoTime();
        System.out.println(String.format("Method '%s', execution time %d", beanMethod.getName(), end - start));
        return invoke;
    }
}
