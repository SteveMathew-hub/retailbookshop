package com.retail.retailbookshop.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.retail.retailbookshop.dto.OrderDTO;
import com.retail.retailbookshop.exception.RetailBookShopException;
import com.retail.retailbookshop.service.OrderService;

@RestController
@RequestMapping("/order-api")
public class OrderAPI {

	@Autowired
	Environment environment;

	@Autowired
	private OrderService orderService;

	static Log logger = LogFactory.getLog(OrderAPI.class);

	@PostMapping("/customer/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) throws RetailBookShopException {

		String orderId = orderService.placeOrder(orderDTO);
		logger.info(environment.getProperty("OrderAPI.ORDER_PLACED") + orderId);
		return new ResponseEntity<>(orderId, HttpStatus.CREATED);
	}

}
