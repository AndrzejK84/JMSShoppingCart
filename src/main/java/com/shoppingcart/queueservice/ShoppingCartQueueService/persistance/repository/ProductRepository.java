package com.shoppingcart.queueservice.ShoppingCartQueueService.persistance.repository;

import com.shoppingcart.queueservice.ShoppingCartQueueService.persistance.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
