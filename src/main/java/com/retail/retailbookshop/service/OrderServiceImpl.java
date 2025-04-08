package com.retail.retailbookshop.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.retailbookshop.dto.OrderDTO;
//import com.retail.retailbookshop.entity.BookInventory;
import com.retail.retailbookshop.document.BookInventory;
//import com.retail.retailbookshop.entity.Order;
import com.retail.retailbookshop.document.Order;
import com.retail.retailbookshop.exception.RetailBookShopException;
import com.retail.retailbookshop.repository.BookInventoryRepository;
import com.retail.retailbookshop.repository.OrderRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	BookInventoryRepository bookInventoryRepository;

	@Override
	public String placeOrder(OrderDTO orderDTO) throws RetailBookShopException {

		Optional<BookInventory> bookOptional = bookInventoryRepository.findById(orderDTO.getBookId());
		BookInventory book = bookOptional
				.orElseThrow(() -> new RetailBookShopException("BookInventoryService.BOOK_NOT_AVAILABLE"));

		if (book.getQuantity() < 1) {
			throw new RetailBookShopException("OrderServie.BOOK_OUT_OF_STOCK");
		}

		Order order = new Order();
		order.setCustomerEmailId(orderDTO.getCustomerEmailId());
		order.setBookId(orderDTO.getBookId());

		Order placedOrder = orderRepository.save(order);
		return placedOrder.getOrderId();
	}

}
