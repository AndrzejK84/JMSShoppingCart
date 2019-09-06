package com.shoppingcart.queueservice.ShoppingCartQueueService;

import com.shoppingcart.queueservice.ShoppingCartQueueService.persistance.domain.Product;
import com.shoppingcart.queueservice.ShoppingCartQueueService.persistance.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@TestPropertySource(properties = {"queue-name=TEST_QUEUE"})
class ShoppingCartQueueServiceApplicationTests {

    @Autowired
    private JmsTemplate template;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
    }


    @Test
    public void test() throws Exception {
        // given
        Product testProduct = new Product(123L, "bar", 2.5, new BigDecimal(6.6).setScale(5, BigDecimal.ROUND_HALF_UP));

        // when
        this.template.convertAndSend("TEST_QUEUE", testProduct);

        // then
        Product product = productRepository.findAll().iterator().next();
        assertThat(product.getCode()).isEqualTo(testProduct.getCode());
        assertThat(product.getName()).isEqualTo(testProduct.getName());
        assertThat(product.getWeight()).isEqualTo(testProduct.getWeight());
        assertThat(product.getPrice()).isEqualTo(testProduct.getPrice());
    }

}
