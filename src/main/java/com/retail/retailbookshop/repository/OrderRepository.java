package com.retail.retailbookshop.repository;

import org.springframework.data.repository.CrudRepository;

//import com.retail.retailbookshop.entity.Order;

import com.retail.retailbookshop.document.Order;

public interface OrderRepository extends CrudRepository<Order, String>{

}
