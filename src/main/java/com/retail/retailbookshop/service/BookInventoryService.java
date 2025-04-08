package com.retail.retailbookshop.service;
import java.util.List;

import com.retail.retailbookshop.dto.BookInventoryDTO;
import com.retail.retailbookshop.exception.RetailBookShopException;

public interface BookInventoryService {
	
	List<BookInventoryDTO> getAllBooks() throws RetailBookShopException;
	
	BookInventoryDTO getBookById(String bookId) throws RetailBookShopException;
	
	String addBook(BookInventoryDTO bookDTO);
	
	void reduceQuantity(String bookId) throws RetailBookShopException;

}
