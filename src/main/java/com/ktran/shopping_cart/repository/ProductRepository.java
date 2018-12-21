package com.ktran.shopping_cart.repository;

import com.ktran.shopping_cart.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {
}
