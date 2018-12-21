package com.ktran.shopping_cart.controller;

import com.ktran.shopping_cart.dto.OrderProductDto;
import com.ktran.shopping_cart.exception.ResourceNotFoundException;
import com.ktran.shopping_cart.model.Order;
import com.ktran.shopping_cart.model.OrderProduct;
import com.ktran.shopping_cart.model.Product;
import com.ktran.shopping_cart.service.OrderProductService;
import com.ktran.shopping_cart.service.OrderProductServiceImpl;
import com.ktran.shopping_cart.service.OrderService;
import com.ktran.shopping_cart.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private ProductService productService;
    private OrderService orderService;
    private OrderProductService orderProductService;

    OrderController(ProductService productService, OrderService orderService, OrderProductService orderProductService) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }


    @GetMapping
    public ResponseEntity<List<?>> getOrders() {
        List<SimpleOrderForm> orders = new ArrayList<>();

        orderService.getAllOrders().forEach(order  -> {
            SimpleOrderForm simpleOrderForm = new SimpleOrderForm();
            simpleOrderForm.setId(order.getId());
            simpleOrderForm.setStatus(order.getStatus());
            orders.add(simpleOrderForm);
        });

        return new ResponseEntity<>(orders,null,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderForm form){
        List<OrderProductDto> formDtos = form.getOrderProducts();
        validateProductExistence(formDtos);

        Order order = orderService.createOrder(formDtos);

        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/orders/{id}")
                .buildAndExpand(order.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Order> updateOrderQuantity(@RequestBody OrderForm form) {
        List<OrderProductDto> formDtos = form.getOrderProducts();
        validateProductExistence(formDtos);

        long id = form.getId();

        Order order = orderService.getOrder(id);

        if(order == null) {
            throw new ResourceNotFoundException("Order not found");
        }

        List<OrderProduct> list = order.getOrderProducts();

        formDtos.forEach(orderProductDto -> {
            long productId = orderProductDto.getProduct().getId();
            OrderProduct orderProduct = list.stream().filter(innerOrderProduct -> innerOrderProduct.getProduct().getId()==productId).findFirst().orElse(null);

            orderProduct.setQuantity(orderProductDto.getQuantity());

            orderProductService.create(orderProduct);

        });

        order = orderService.update(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @DeleteMapping(value="/{id}")
    public boolean cancelOrder(@PathVariable long id) {
        orderService.cancelOrder(id);
        return true;
    }


    private void validateProductExistence(List<OrderProductDto> orderProducts ){
        List<OrderProductDto> list = orderProducts
                                    .stream()
                                    .filter(op -> {
                                        return Objects.isNull(productService.getProduct(op.getProduct().getId()));
                                    })
                                    .collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(list)) {
            throw new ResourceNotFoundException("Product not found");
        }

    }

    static class OrderForm {

        private long id;

        private List<OrderProductDto> orderProducts;

        public List<OrderProductDto> getOrderProducts() {
            return orderProducts;
        }

        public void setOrderProducts(List<OrderProductDto> orderProducts) {
            this.orderProducts = orderProducts;
        }

        public long getId() {
            return id;
        }

        public void setId(long orderId) {
            this.id = orderId;
        }
    }

    static class SimpleOrderForm {
        private long id;
        private String status;

        public String getStatus() {
            return status;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}



