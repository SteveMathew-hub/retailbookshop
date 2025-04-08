package com.retail.retailbookshop.api;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.retailbookshop.dto.UserDTO;
import com.retail.retailbookshop.exception.RetailBookShopException;
import com.retail.retailbookshop.service.UserService;

@RestController
@RequestMapping("/user")
public class UserAPI {

	@Autowired
	private UserService userService;

	@Autowired
	Environment environment;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	static Log logger = LogFactory.getLog(UserAPI.class);

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) throws RetailBookShopException {
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		String email = userService.addUser(userDTO);
		String message = environment.getProperty("UserApi.USER_ADDED_SUCCESSFULLY") + email;
		logger.info(message);
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

}
