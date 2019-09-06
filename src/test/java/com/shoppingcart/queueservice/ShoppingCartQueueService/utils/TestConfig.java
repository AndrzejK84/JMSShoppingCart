package com.shoppingcart.queueservice.ShoppingCartQueueService.utils;

import com.shoppingcart.queueservice.ShoppingCartQueueService.service.ProductConsumer;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;


@TestConfiguration
public class TestConfig {

    private static final String LISTENER_METHOD_NAME = "receiveMessage";

    private static final CountDownLatch LATCH = new CountDownLatch(1);

    private static Object received;

    @Bean
    public static BeanPostProcessor listenerWrapper() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof ProductConsumer) {
                    MethodInterceptor interceptor = new MethodInterceptor() {

                        @Override
                        public Object invoke(MethodInvocation invocation) throws Throwable {
                            Object result = invocation.proceed();
                            if (invocation.getMethod().getName().equals(LISTENER_METHOD_NAME)) {
                                received = invocation.getArguments()[0];
                                LATCH.countDown();
                            }
                            return result;
                        }
                    };
                    if (AopUtils.isAopProxy(bean)) {
                        ((Advised) bean).addAdvice(interceptor);
                        return bean;
                    } else {
                        ProxyFactory proxyFactory = new ProxyFactory(bean);
                        proxyFactory.addAdvice(interceptor);
                        return proxyFactory.getProxy();
                    }
                } else {
                    return bean;
                }
            }

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }

    public static CountDownLatch getLATCH() {
        return LATCH;
    }

    public static Object getReceived() {
        return received;
    }
}
