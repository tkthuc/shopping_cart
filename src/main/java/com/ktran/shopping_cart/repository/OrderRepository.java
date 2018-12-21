package com.ktran.shopping_cart.repository;

import com.ktran.shopping_cart.model.Order;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Order,Long> {
}
