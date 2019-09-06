package com.shoppingcart.queueservice.ShoppingCartQueueService.service;

import com.shoppingcart.queueservice.ShoppingCartQueueService.ShoppingCartQueueServiceApplication;
import com.shoppingcart.queueservice.ShoppingCartQueueService.persistance.domain.Product;
import com.shoppingcart.queueservice.ShoppingCartQueueService.utils.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ShoppingCartQueueServiceApplication.class, TestConfig.class})
@TestPropertySource(properties = {"queue-name=TEST_QUEUE"})
class ProductConsumerTest {

    @Autowired
    private JmsTemplate template;

    @Test
    public void test() throws Exception {

        // given
        Product testProduct = new Product(123L, "bar", 2.5, new BigDecimal(6.6));

        // when
        this.template.convertAndSend("TEST_QUEUE", testProduct);

        // then
        assertThat(TestConfig.getLATCH().await(10, TimeUnit.SECONDS)).isTrue();

        Product recievedProduct = (Product) TestConfig.getReceived();
        assertThat(recievedProduct.getCode()).isEqualTo(testProduct.getCode());
        assertThat(recievedProduct.getName()).isEqualTo(testProduct.getName());
        assertThat(recievedProduct.getWeight()).isEqualTo(testProduct.getWeight());
        assertThat(recievedProduct.getPrice()).isEqualTo(testProduct.getPrice());
    }

}
