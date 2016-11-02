package ua.rd.pizzaservice.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BenchMarkBeanPostProcessor<T> implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Created " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // System.out.println("Initialized " + beanName);
        return createBeanProxy(bean);
    }

    public T createBeanProxy(Object bean) {
        if (hasMethodAnnotation(BenchMark.class, bean)) {
            System.out.println("Bench " + bean);
            T original = (T) bean;
            Class<?> type = bean.getClass();
            Class<?>[] declaredInterfaces = getDeclaredInterfaces(bean);
            System.out.println("Declared interfaces" + Arrays.toString(declaredInterfaces));
            bean = (T) Proxy.newProxyInstance(type.getClassLoader(), declaredInterfaces,
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Method beanMethod = type.getMethod(method.getName(), method.getParameterTypes());
                            System.out.println(beanMethod);
                            if (isBenchMarkAnnotationPresentAndTrue(beanMethod)) {
                                System.out.println("BenchmarkAnnot " + type);
                                return wrapMethodInBenchmark(original, beanMethod, args);
                            } else return beanMethod.invoke(original, args);
                        }
                    });
        }
        return (T) bean;
    }

    private Class<?>[] getDeclaredInterfaces(Object o) {
        List<Class<?>> interfaces = new ArrayList<>();
        Class<?> klazz = o.getClass();
        while (klazz != null) {
            Collections.addAll(interfaces, klazz.getInterfaces());
            klazz = klazz.getSuperclass();
        }
        return interfaces.stream().toArray(Class<?>[]::new);
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
    public boolean hasMethodAnnotation(Class<? extends Annotation> annotationClass, Object bean) {
        Class<?>[] classes=getDeclaredClasses(bean);
        for (Class item:classes){
            Method [] methods=item.getDeclaredMethods();
            for (Method method:methods){
                if (method.isAnnotationPresent(annotationClass)){return true;}
            }
        }
      return false;
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
