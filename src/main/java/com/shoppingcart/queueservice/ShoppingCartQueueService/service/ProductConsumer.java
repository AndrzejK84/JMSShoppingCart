package com.shoppingcart.queueservice.ShoppingCartQueueService.service;

import com.shoppingcart.queueservice.ShoppingCartQueueService.persistance.domain.Product;
import com.shoppingcart.queueservice.ShoppingCartQueueService.persistance.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    private ProductRepository productRepository;


    @Autowired
    public ProductConsumer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @JmsListener(destination = "${queue-name}", containerFactory = "queueListenerFactory")
    public void receiveMessage(Product product) {
        Product savedProduct = productRepository.save(product);
        if (savedProduct.getClass() != Product.class) {
            // in case something goes wrong while saving product to database, execption thrown prevent spring from acknowledging message
            new RuntimeException("Message could not be consumed. Transaction rolled back.");
        }
    }
}
