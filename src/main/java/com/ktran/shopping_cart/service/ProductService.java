package com.ktran.shopping_cart.service;

import com.ktran.shopping_cart.model.Product;
import org.springframework.cache.annotation.Cacheable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface ProductService {

    @Cacheable("products")
    @NotNull Iterable<Product> getAllProducts();


    Product getProduct(@Min(value=1L,message="Invalid product ID") long id);

    Product save(Product product);

}
