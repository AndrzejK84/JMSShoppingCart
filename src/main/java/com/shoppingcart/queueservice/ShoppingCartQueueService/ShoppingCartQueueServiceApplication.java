package com.shoppingcart.queueservice.ShoppingCartQueueService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ShoppingCartQueueServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartQueueServiceApplication.class, args);
    }

}
