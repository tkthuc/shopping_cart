package com.ktran.shopping_cart.service;

import com.ktran.shopping_cart.dto.OrderProductDto;
import com.ktran.shopping_cart.exception.ResourceNotFoundException;
import com.ktran.shopping_cart.model.Order;
import com.ktran.shopping_cart.model.OrderProduct;
import com.ktran.shopping_cart.model.OrderStatus;
import com.ktran.shopping_cart.repository.OrderProductRepository;
import com.ktran.shopping_cart.repository.OrderRepository;
import com.ktran.shopping_cart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    OrderProductService orderProductService;

    @Override
    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order createOrder(List<OrderProductDto> formDtos) {
        Order order = new Order();

        order.setStatus(OrderStatus.PAID.name());

        order = orderRepository.save(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for(OrderProductDto dto: formDtos) {
            OrderProduct orderProduct = new OrderProduct(order, productService.getProduct(dto
                    .getProduct()
                    .getId()), dto.getQuantity());
            orderProducts.add(orderProductService.create(orderProduct));
        }

        order.setOrderProducts(orderProducts);

        return this.orderRepository.save(order);
    }


    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }


    @Override
    public Order getOrder(long id) {
        return orderRepository.findById(id).orElse(null);
    }


    public void cancelOrder(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        List<OrderProduct> orderProducts = order.getOrderProducts();


        orderProducts.forEach(orderProductService::delete);


        orderRepository.delete(order);
    }
}
