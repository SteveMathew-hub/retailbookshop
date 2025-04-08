package com.retail.retailbookshop.service;

import com.retail.retailbookshop.dto.UserDTO;
import com.retail.retailbookshop.exception.RetailBookShopException;

public interface UserService {
	
	public String addUser(UserDTO userDTO) throws RetailBookShopException;
	
	public UserDTO getUserByEmail(String email) throws RetailBookShopException;
	
	public UserDTO authenticateUser(String email, String password, String role) throws RetailBookShopException;

}
