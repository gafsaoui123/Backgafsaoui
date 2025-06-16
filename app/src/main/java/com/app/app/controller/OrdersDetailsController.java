package com.app.app.controller;


import com.app.app.entity.OrderInput;
import com.app.app.service.OrderDetailsService;
import com.app.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;


    @PreAuthorize("hasRole('User')")
    @PostMapping({"/placeOrder"})
    public void placeOrder(@RequestBody OrderInput orderInput) {
          orderDetailsService.placeOrder(orderInput);
    }
}
