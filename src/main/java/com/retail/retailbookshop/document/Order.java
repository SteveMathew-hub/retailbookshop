package com.retail.retailbookshop.document;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
public class Order {

	@Id
	private String orderId;

	@Field("book_id")
	private String bookId;

	@Field("customer_email_id")
	private String customerEmailId;

	public Order() {
		this.orderId = idGenerator();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}

	public String idGenerator() {
		UUID uuid = UUID.randomUUID();
		return "rs_order_" + uuid.hashCode();
	}

}
