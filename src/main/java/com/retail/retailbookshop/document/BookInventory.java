package com.retail.retailbookshop.document;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
public class BookInventory {

	@Id
	private String bookID;

	@Field("name")
	private String name;

	@Field("description")
	private String description;

	@Field("category")
	private String category;

	@Field("pages")
	private Integer numberOfPages;

	@Field("price")
	private Double price;

	@Field("quantity")
	private Integer quantity;

	public BookInventory() {
		this.bookID = idGenerator();
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String idGenerator() {
		UUID uuid = UUID.randomUUID();
		return "rs_book_" + uuid.hashCode();
	}

}
