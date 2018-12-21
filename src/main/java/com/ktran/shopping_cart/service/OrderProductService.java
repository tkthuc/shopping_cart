package com.ktran.shopping_cart.service;

import com.ktran.shopping_cart.model.OrderProduct;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface OrderProductService {

    OrderProduct create(@NotNull(message = "The products for order cannot be null.") @Valid OrderProduct orderProduct);

    OrderProduct find(long id);

    void delete(OrderProduct orderProduct);

}
