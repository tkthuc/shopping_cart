package com.ktran.shopping_cart.repository;

import com.ktran.shopping_cart.model.OrderProduct;
import com.ktran.shopping_cart.model.OrderProductPK;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
    OrderProduct findByOrderProductPk(OrderProductPK orderProductPK);
}
