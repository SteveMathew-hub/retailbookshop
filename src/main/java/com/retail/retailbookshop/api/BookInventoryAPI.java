package com.retail.retailbookshop.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.retailbookshop.dto.BookInventoryDTO;
import com.retail.retailbookshop.exception.RetailBookShopException;
import com.retail.retailbookshop.service.BookInventoryService;

@RestController
@RequestMapping("/bookInventory-api")
public class BookInventoryAPI {

	@Autowired
	private BookInventoryService bookInventoryService;

	@Autowired
	private Environment environment;

	static Log logger = LogFactory.getLog(BookInventoryAPI.class);

	@GetMapping("/book/allBooks")
	public ResponseEntity<List<BookInventoryDTO>> getAllBooks() throws RetailBookShopException {
		List<BookInventoryDTO> bookDTOs = bookInventoryService.getAllBooks();
		logger.info(environment.getProperty("BookInventoryAPI.GET_ALL"));
		return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<BookInventoryDTO> getBook(@PathVariable("id") String id) throws RetailBookShopException {
		BookInventoryDTO bookDTO = bookInventoryService.getBookById(id);
		logger.info(environment.getProperty("BookInventoryAPI.GET_BY_ID") + bookDTO.getBookID());
		return new ResponseEntity<>(bookDTO, HttpStatus.OK);
	}

	// http://localhost:8786/bookInventory/admin/add
	@PostMapping("/admin/add")
	public ResponseEntity<String> addBook(@Valid @RequestBody BookInventoryDTO bookDTO) {
		String message = bookInventoryService.addBook(bookDTO);
		logger.info(message);
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	@PutMapping("/customer/update/{id}")
	public ResponseEntity<String> reduceQuantity(@PathVariable String id) throws RetailBookShopException {

		bookInventoryService.reduceQuantity(id);
		logger.info(environment.getProperty("ProductAPI.REDUCE_QUANTITY"));
		return new ResponseEntity<>(environment.getProperty("ProductAPI.REDUCE_QUANTITY"), HttpStatus.OK);
	}

}
