package com.retail.retailbookshop.service;

import com.retail.retailbookshop.dto.OrderDTO;
import com.retail.retailbookshop.exception.RetailBookShopException;

public interface OrderService {

	public String placeOrder(OrderDTO orderDTO) throws RetailBookShopException;

}
