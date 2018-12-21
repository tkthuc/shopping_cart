package com.ktran.shopping_cart.service;

import com.ktran.shopping_cart.dto.OrderProductDto;
import com.ktran.shopping_cart.model.Order;

import java.util.List;

public interface OrderService {

    Iterable<Order> getAllOrders();

    Order createOrder(List<OrderProductDto> formDtos);

    Order update(Order order);

    Order getOrder(long id);

    void cancelOrder(long id);


}
