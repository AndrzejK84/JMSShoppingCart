package com.shoppingcart.queueservice.ShoppingCartQueueService.persistance.domain;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "PRODUCT_ENTITY")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;

    @Column(nullable = false)
    private Long code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double weight;
    @Column(precision = 10, scale = 5, nullable = false)
    private BigDecimal price;

    public Product() {
    }

    public Product(Long code, String name, Double weight, BigDecimal price) {
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", version=" + version +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}
