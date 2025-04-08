package com.retail.retailbookshop.repository;

//import org.springframework.data.couchbase.repository.CouchbaseRepository;

import org.springframework.data.repository.CrudRepository;

import com.retail.retailbookshop.document.BookInventory;

public interface BookInventoryRepository extends CrudRepository<BookInventory, String>{
	

}
