package com.retail.retailbookshop.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.retail.retailbookshop.document.User;

//import com.retail.retailbookshop.entity.User;

public interface UserRepository extends CrudRepository<User, String>{
	
	Optional<User> findByEmail(String email);

}
