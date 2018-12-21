package com.ktran.shopping_cart.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/orders")
public class OrderRequestFilter implements Filter {
    private final Logger log = LoggerFactory.getLogger(OrderRequestFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        log.info("I am inside the Order Request Filter");
    }
}
