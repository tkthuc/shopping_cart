package com.ktran.shopping_cart.service;

import com.ktran.shopping_cart.exception.ResourceNotFoundException;
import com.ktran.shopping_cart.model.OrderProduct;
import com.ktran.shopping_cart.repository.OrderProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public OrderProduct create(OrderProduct orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }

    @Override
    public OrderProduct find(long id) {
        return this.orderProductRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(OrderProduct orderProduct) {
        OrderProduct orderProduct1 = orderProductRepository.findByOrderProductPk(orderProduct.getOrderProductPk());
        orderProductRepository.delete(orderProduct1);
    }
}
