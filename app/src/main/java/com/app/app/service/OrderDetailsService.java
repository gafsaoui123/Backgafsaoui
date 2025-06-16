package com.app.app.service;


import com.app.app.configuration.JwtRequestFilter;
import com.app.app.dao.OrderDetailDao;
import com.app.app.dao.ProductDao;
import com.app.app.dao.UserDao;
import com.app.app.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {



    private static final  String ORDER_PLACED = "Placed";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public void placeOrder(OrderInput orderInput) {
      List <OrderProductQuantity> productQuantityList=orderInput.getOrderProductQuantityList();


      for(OrderProductQuantity o:productQuantityList){
          Product product = productDao.findById(o.getProductId()).get();

          String currentuser = JwtRequestFilter.CURRENT_USER;

          System.out.println("CURRENT_USER = " + currentuser);
          System.out.println("User present = " + userDao.findById(currentuser).isPresent());

          User user = userDao.findById(currentuser).get();


           OrderDetail orderDetail=new OrderDetail(
                   user,
                   product,
                   product.getProductDiscountedPrice() * o.getQuantity(),
                   orderInput.getContactNumber(),
                   ORDER_PLACED,
                   orderInput.getAlternateContactNumber(),
                   orderInput.getFullAddress(),
                   orderInput.getFullName()



           );

           orderDetailDao.save(orderDetail);
      }
    }
}
