package com.retail.retailbookshop.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class BookInventoryDTO {

	private String bookID;

	private String name;

	@Size(min = 15, message = "{book.invalid.description}")
	private String description;

	@Pattern(regexp = "(Fictional|Non-fictional)", message = "{book.invalid.category}")
	private String category;

	@Min(value = 1, message = "{book.invalid.price.min}")
	@Max(value = 1000000, message = "{book.invalid.price.max}")
	private Double price;

	@Min(value = 1, message = "{book.invalid.pages}")
	private Integer numberOfPages;

	@Min(value = 1, message = "{book.invalid.quanity}")
	private Integer quantity;

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

}
