package com.ktran.shopping_cart.controller;


import com.ktran.shopping_cart.exception.ResourceNotFoundException;
import com.ktran.shopping_cart.model.Product;
import com.ktran.shopping_cart.service.ProductService;
import org.hibernate.service.spi.InjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/products")
public class ProductController {


    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = {"","/"})
    public @NotNull Iterable<Product> getProducts(){
        return productService.getAllProducts();
    }


    @GetMapping(value ="/{id}")
    public Product getProduct(@PathVariable long id) {
        log.info("Get product with id = {}",id);
        Product product = productService.getProduct(id);
        return product;
    }

    @PutMapping(value="/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product productInfo){
        return productService.save(productInfo);
    }


}
