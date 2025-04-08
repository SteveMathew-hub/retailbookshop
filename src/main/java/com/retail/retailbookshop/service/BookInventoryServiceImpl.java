package com.retail.retailbookshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.retail.retailbookshop.dto.BookInventoryDTO;
//import com.retail.retailbookshop.entity.BookInventory;
import com.retail.retailbookshop.document.BookInventory;
import com.retail.retailbookshop.exception.RetailBookShopException;
import com.retail.retailbookshop.repository.BookInventoryRepository;

@Service
@Transactional
public class BookInventoryServiceImpl implements BookInventoryService {

	@Autowired
	BookInventoryRepository bookInventoryRepository;

	@Autowired
	Environment environment;

	@Override
	public List<BookInventoryDTO> getAllBooks() throws RetailBookShopException {

		Iterable<BookInventory> books = bookInventoryRepository.findAll();
		List<BookInventoryDTO> bookDTOs = new ArrayList<>();
		for (BookInventory book : books) {
			bookDTOs.add(mapToBookInventoryDTO(book));
		}

		if (bookDTOs.isEmpty()) {
			throw new RetailBookShopException("BookInventoryService.BOOKS_NOT_AVAILABLE");
		}

		return bookDTOs;
	}

	@Override
	public BookInventoryDTO getBookById(String bookId) throws RetailBookShopException {
		Optional<BookInventory> optional = bookInventoryRepository.findById(bookId);
		BookInventory book = optional
				.orElseThrow(() -> new RetailBookShopException("BookInventoryService.BOOK_NOT_AVAILABLE"));

		return mapToBookInventoryDTO(book);

	}

	// add books. available only to administrator user.
	@Override
	public String addBook(BookInventoryDTO bookDTO) {
		BookInventory book = new BookInventory();

		book.setCategory(bookDTO.getCategory());
		book.setDescription(bookDTO.getDescription());
		book.setName(bookDTO.getName());
		book.setNumberOfPages(bookDTO.getNumberOfPages());
		book.setPrice(bookDTO.getPrice());
		book.setQuantity(bookDTO.getQuantity());

		BookInventory addedBook = bookInventoryRepository.save(book);
		String msg = environment.getProperty("BookInventoryService.BOOK_ADDED_SUCCESSFULLY") + " "
				+ addedBook.getBookID();
		return msg;
	}

	// reducing the quantity of book in the inventory.
	@Override
	public void reduceQuantity(String bookId) throws RetailBookShopException {
		Optional<BookInventory> optional = bookInventoryRepository.findById(bookId);
		BookInventory book = optional
				.orElseThrow(() -> new RetailBookShopException("BookInvetoryService.BOOK_NOT_AVAILABLE"));
		if (book.getQuantity() <= 0) {
			throw new RetailBookShopException("BookInventoryService.BOOK_NOT_AVAILABLE");
		}
		book.setQuantity(book.getQuantity() - 1);
		bookInventoryRepository.save(book);

	}

	public BookInventoryDTO mapToBookInventoryDTO(BookInventory book) {
		BookInventoryDTO bookDTO = new BookInventoryDTO();
		bookDTO.setBookID(book.getBookID());
		bookDTO.setName(book.getName());
		bookDTO.setCategory(book.getCategory());
		bookDTO.setDescription(book.getDescription());
		bookDTO.setNumberOfPages(book.getNumberOfPages());
		bookDTO.setPrice(book.getPrice());
		bookDTO.setQuantity(book.getQuantity());

		return bookDTO;
	}

}
